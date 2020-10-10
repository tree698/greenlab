package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.Photo;
import web.data.repogitory.PhotoRepository;

/**
 * 고객정보 관련 Serivce
 */
@Slf4j
@Service
public class PhotoService {
	
	/**
	 * 쓰레기 이미지 레포지토리
	 */
	@Autowired
	private PhotoRepository photoRepository;
	
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
}
