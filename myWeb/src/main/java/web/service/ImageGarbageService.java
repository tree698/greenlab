package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.ImageGarbage;
import web.data.repogitory.ImageGarbageRepository;

/**
 * 고객정보 관련 Serivce
 */
@Slf4j
@Service
public class ImageGarbageService {
	
	/**
	 * 쓰레기 이미지 레포지토리
	 */
	@Autowired
	private ImageGarbageRepository imageGarbageRepository;
	
	/**
	 * 짧은소식 불러오기 (리스트)
	 */
	public Page<ImageGarbage> getImageGarbageList(String imageType, Pageable page) {
		Page<ImageGarbage> garbageList = imageGarbageRepository.findByImageType(imageType, page);
		return garbageList;
	}
}
