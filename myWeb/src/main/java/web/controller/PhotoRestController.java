package web.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import web.adapter.AsyncAdapter;
import web.data.entity.Photo;
import web.data.repogitory.PhotoRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.time.ZonedDateTime;

@Slf4j
@RestController
@ConfigurationProperties("image.garbage")
@RequestMapping("/api/images/garbage")
public class PhotoRestController {
    private static final Tika TIKA = new Tika();

    private final AsyncAdapter asyncAdapter;
    private final PhotoRepository imageGarbageRepository;
    
    @Getter
    @Setter
    private Resource resource;
    
    public PhotoRestController(AsyncAdapter asyncAdapter,
                                  PhotoRepository imageGarbageRepository) {
        this.asyncAdapter = asyncAdapter;
        this.imageGarbageRepository = imageGarbageRepository;
    }

    @GetMapping
    public Page<ImageGarbageGetResponse> get(Pageable pageable, UriComponentsBuilder builder) {
        return this.imageGarbageRepository.findAll(pageable)
                .map(ImageGarbageGetResponse::new)
                .map(response -> response.with(builder.cloneBuilder()));
    }

    @PostMapping
    public ImageGarbageGetResponse post(MultipartFile file, String title, UriComponentsBuilder builder) {
        try (InputStream is = file.getInputStream()) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            byte[] byteArray = FileCopyUtils.copyToByteArray(is);
            String originalFilename = file.getOriginalFilename();
            String extension = StringUtils.getFilenameExtension(originalFilename);
            if (!StringUtils.hasText(extension)) {
                extension = MediaType.parseMediaType(file.getContentType()).getSubtype();
            }
            extension = MediaType.parseMediaType(TIKA.detect(byteArray, "file." + extension)).getSubtype();
            String filename = DigestUtils.md5DigestAsHex(("|" + zonedDateTime.toInstant().toEpochMilli() + "|" + originalFilename + "|").getBytes()) + "." + extension;

            Resource createRelative = resource.createRelative(zonedDateTime.getYear() + "/")
                    .createRelative(zonedDateTime.getMonthValue() + "/")
                    .createRelative(zonedDateTime.getDayOfMonth() + "/");

            String path = resource.getFile().getAbsoluteFile().toURI().relativize(createRelative.getFile().getAbsoluteFile().toURI()).getPath();

            Files.createDirectories(createRelative.getFile().toPath());
            asyncAdapter.resolve(() -> {
                try {
                    FileCopyUtils.copy(byteArray, createRelative.createRelative(filename).getFile());
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
                return null;
            });
            Photo imageGarbage = new Photo();
            imageGarbage.setTitle(title);
            imageGarbage.setPath(path);
            imageGarbage.setFilename(filename);
            imageGarbage.setOriginalFilename(originalFilename);
            return new ImageGarbageGetResponse(imageGarbageRepository.save(imageGarbage))
                    .with(builder.cloneBuilder());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @NoArgsConstructor
    public static class ImageGarbageGetResponse {
        public ImageGarbageGetResponse(Photo imageGarbage) {
            this.title = imageGarbage.getTitle();
            this.path = imageGarbage.getPath();
            this.filename = imageGarbage.getFilename();
        }

        @Getter
        private String title;
        @Getter
        private URI uri;
        private String path;
        private String filename;

        public ImageGarbageGetResponse with(UriComponentsBuilder builder) {
            this.uri = builder.path(path).pathSegment("{filename}").build(filename);
            return this;
        }
    }
}
