package web.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
import web.adapter.AsyncAdapter;
import web.data.entity.Photo;
import web.data.entity.WishPhoto;
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
			String extension = StringUtils.getFilenameExtension(originalFilename);
			if (!StringUtils.hasText(extension)) {
				extension = MediaType.parseMediaType(photo.getContentType()).getSubtype();
			}
			extension = MediaType.parseMediaType(TIKA.detect(byteArray, "file." + extension)).getSubtype();
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
					FileCopyUtils.copy(byteArray, createRelative.createRelative(filename).getFile());
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
	public void saveWishNews(int photoId, int userId) {
		WishPhoto wishPhoto = new WishPhoto(photoId, userId, LocalDateTime.now());
		wishPhotoRepo.save(wishPhoto);
	}
}
