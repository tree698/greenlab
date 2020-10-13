package web.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import web.adapter.AsyncAdapter;
import web.data.entity.Photo;
import web.data.entity.WishPhoto;
import web.data.entity.WishPhotoPK;
import web.data.repogitory.PhotoRepository;
import web.data.repogitory.WishPhotoRepo;

/**
 * 고객정보 관련 Serivce
 */
@Slf4j
@Service
@ConfigurationProperties("photo.upload")
public class PhotoService {
	
	private static final Tika TIKA = new Tika();
	
	@Getter
	@Setter
	private Resource resource;
	
	/**
	 * 쓰레기 이미지 레포지토리
	 */
	@Autowired
	private PhotoRepository photoRepository;
	
	@Autowired
	WishPhotoRepo wishPhotoRepo;
	
	
	public Photo multipartFileToPhoto(AsyncAdapter asyncAdapter, MultipartFile photo, String type) {
		
		try (InputStream is = photo.getInputStream()) {
			ZonedDateTime zonedDateTime = ZonedDateTime.now();
			byte[] byteArray = FileCopyUtils.copyToByteArray(is);
			String originalFilename = photo.getOriginalFilename();
			String extension = extension(originalFilename, photo.getContentType(), byteArray);
			String filename = DigestUtils.md5DigestAsHex(
					("|" + zonedDateTime.toInstant().toEpochMilli() + "|" + originalFilename + "|").getBytes()) + "."
					+ extension;

			Resource createRelative = resource.createRelative("G".equals(type) ? "/garbage/" : "/lunch/");
			/* zonedDateTime.getYear() + "/") .createRelative(zonedDateTime.getMonthValue()
			 * + "/") .createRelative(zonedDateTime.getDayOfMonth() + "/"
			); */

			String path = resource.getFile().getAbsoluteFile().toURI()
					.relativize(createRelative.getFile().getAbsoluteFile().toURI()).getPath();

			Files.createDirectories(createRelative.getFile().toPath());
			asyncAdapter.resolve(() -> {
				try {
                    byte[] in = StreamSupport.stream(((Iterable<ImageReader>) () -> ImageIO.getImageReadersBySuffix(extension)).spliterator(), false)
                            //포맷 가능 조회
                            .findFirst()
                            //첫번째 꺼 사용
                            .map(imageReader -> {
                                try {
                                    return imageReader.getFormatName();
                                } catch (IOException e) {
                                    return null;
                                }
                            })
                            //포맷 이름 추출
                            .map(format -> {
                                ByteArrayOutputStream os = new ByteArrayOutputStream();
                                try {
                                    //BufferedImage 로 한번 컨버팅 후 (exif)
                                    //byte 로 포맷 타입 지정해서 변환
                                    Thumbnails.of(Thumbnails.of(new ByteArrayInputStream(byteArray))
                                            .imageType(BufferedImage.TYPE_INT_ARGB)
                                            .scale(1.0D)
                                            .asBufferedImage())
                                            .scale(1.0D)
                                            .outputFormat(format)
                                            .toOutputStream(os);
                                } catch (IOException e) {
                                    return null;
                                }
                                return os.toByteArray();
                            })
                            .orElse(byteArray);
					FileCopyUtils.copy(in, createRelative.createRelative(filename).getFile());
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
				return null;
			});
			
			Photo imageGarbage = new Photo();
			imageGarbage.setTitle(filename);
			
			imageGarbage.setPath(path);
			imageGarbage.setFilename(filename);
			imageGarbage.setOriginalFilename(originalFilename);
			
			LocalDateTime datetime = LocalDateTime.now();
			imageGarbage.setCreatedDate(datetime);
			imageGarbage.setLastModifiedDate(datetime);
			
			return imageGarbage;
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	/**
	 * 확장자 추출
	 * 원본 파일 이름 > Content Type => tika 로 확장자 최종 결정
	 */
	private String extension(String originalFilename, String contentType, byte[] byteArray){
		String extension = StringUtils.getFilenameExtension(originalFilename);
		if (!StringUtils.hasText(extension)) {
			extension = MediaType.parseMediaType(contentType).getSubtype();
		}
		return MediaType.parseMediaType(TIKA.detect(byteArray, "file." + extension)).getSubtype();
	}

	/**
	 * 사진 불러오기 (리스트)
	 */
	public Page<Photo> getImageGarbageList(String imageType, Pageable page) {
		Page<Photo> garbageList = photoRepository.findByPhotoType(imageType, page);
		return garbageList;
	}
	
	/**
	 * 사진 저장
	 */
	public Photo savePhoto(Photo photo) {
		return photoRepository.save(photo);
	}
	
	
	/**
	 * 사진 찜하기 추가
	 */
	public void saveWishPhoto(int photoId, int userId) {
		WishPhoto wishNews = new WishPhoto(photoId, userId, LocalDateTime.now());
		wishPhotoRepo.save(wishNews);
	}
	/**
	 * 사진 찜하기 제거
	 */
	public void deleteWishPhoto(int photoId, int userId) {
		wishPhotoRepo.deleteById(new WishPhotoPK(photoId, userId));
	}
}
