package web.controller;

import java.net.URI;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.adapter.AsyncAdapter;
import web.data.entity.Photo;
import web.data.entity.UserInfo;
import web.model.ApiResult;
import web.service.PhotoService;

@Slf4j
@RestController
@RequestMapping("/api/images")
public class PhotoRestController {
	private static final Tika TIKA = new Tika();

	private final AsyncAdapter asyncAdapter;
	// private final PhotoRepository imageGarbageRepository;
	
	@Autowired
	private PhotoService photoService;

	public PhotoRestController(AsyncAdapter asyncAdapter) {
		this.asyncAdapter = asyncAdapter;
	}
	
	/**
	 * 쓰레기사진 업로드
	 */
	@PostMapping("garbage")
	public ApiResult garbage(UserInfo userInfo, MultipartFile photo, String location/* , UriComponentsBuilder builder */) {
		
		if (userInfo == null) {
			return new ApiResult(ApiResult.RET_FAIL_CODE, "로그인한 유저만 사진업로드 가능합니다");
		}
		if(photo == null) {
			log.error("Photo is null");
			return new ApiResult(ApiResult.RET_FAIL_CODE, "업로드된 사진이 없습니다");
		}
		if(location == null) {
			log.error("Place info is null");
			return new ApiResult(ApiResult.RET_FAIL_CODE, "장소정보를 입력해주세요");
		}
		
		try {
			Photo photoImg = photoService.multipartFileToPhoto(asyncAdapter, photo, "G");
			if (photoImg == null) {
				return new ApiResult(ApiResult.RET_FAIL_CODE, "사진 업로드에 실패했습니다");
			}
			
			photoImg.setTitle(photoImg.getFilename());
			photoImg.setLocation(location);
			photoImg.setUserId(userInfo.getId());
			
			photoImg.setPhotoType("G");
			
			photoService.savePhoto(photoImg);
			
			return new ApiResult(ApiResult.RET_SUCCESS_CODE);
			
		} catch (Exception e) {
			log.error("upload error", e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
	
	/**
	 * 직장인점심 업로드
	 */
	@PostMapping("lunch")
	public ApiResult lunch(
			/* UriComponentsBuilder builder, */UserInfo userInfo, MultipartFile photo,
					String title, String place, String location, String comment, String category) {
		
		if (userInfo == null) {
			return new ApiResult(ApiResult.RET_FAIL_CODE, "로그인한 유저만 사진업로드 가능합니다");
		}
		if(photo == null) {
			log.error("Photo is null");
			return new ApiResult(ApiResult.RET_FAIL_CODE, "업로드된 사진이 없습니다");
		}
		if(title == null || place == null || location == null || comment == null || category == null) {
			log.error("Image info is nothing");
			return new ApiResult(ApiResult.RET_FAIL_CODE, "장소정보를 입력해주세요");
		}
		
		try {
			Photo photoImg = photoService.multipartFileToPhoto(asyncAdapter, photo, "L");
			if (photoImg == null) {
				return new ApiResult(ApiResult.RET_FAIL_CODE, "사진 업로드에 실패했습니다");
			}
			
			photoImg.setTitle(title);
			photoImg.setPlace(place);
			photoImg.setLocation(location);
			photoImg.setComment(comment);
			photoImg.setCategoryName(category);
			photoImg.setUserId(userInfo.getId());
			
			photoImg.setPhotoType("L");
			
			photoService.savePhoto(photoImg);
			
			return new ApiResult(ApiResult.RET_SUCCESS_CODE);
			
		} catch (Exception e) {
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
	
	/**
	 * 사진 찜하기
	 * @return
	 */
	@PostMapping(path = {"wish"})
	public ApiResult wish(UserInfo userInfo, Integer photoId, boolean isAdd) {
		if (userInfo == null) {
			return new ApiResult(ApiResult.RET_FAIL_CODE, "로그인이 필요합니다");
		}
		if (isAdd) {
			photoService.saveWishPhoto(photoId, userInfo.getId());
		} else {
			photoService.deleteWishPhoto(photoId, userInfo.getId());
		}
		
		
		return new ApiResult(ApiResult.RET_SUCCESS_CODE);
	}
}
