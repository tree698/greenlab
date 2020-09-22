package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.NewsLong;
import web.data.entity.NewsShort;
import web.data.repogitory.NewsLongRepo;
import web.data.repogitory.NewsShortRepo;

/**
 * 고객정보 관련 Serivce
 */
@Slf4j
@Service
public class NewsService {
	
	/**
	 * 긴소식 Data Control
	 */
	@Autowired
	NewsLongRepo newsLongRepo;
	
	/**
	 * 짧은소식 Data Control
	 */
	@Autowired
	NewsShortRepo newsShortRepo;
	
	
	
	/**
	 * 짧은소식 저장
	 * 
	 * @param userInfoForm 입럭할 사용자 정보 (현재기준 Email)
	 * @return 정상저장의 경우 ID값(양의 정수) , 아닌경우 -1
	 */
	public int saveNewsShort(NewsShort newsShort) {
		
		newsShort = newsShortRepo.save(newsShort);
		if (newsShort.getId() == null) {
			log.error("saveNewsShort Save Error. {}", newsShort);
			return -1;
		}
		
		return newsShort.getId();
	}
	
	/**
	 * 짧은소식 불러오기 (리스트)
	 */
	public Page<NewsShort> getNewsShortList(Pageable page) {
		Page<NewsShort> newsShortList = newsShortRepo.findAll(page);
		return newsShortList;
	}
	
	/**
	 * 긴소식 저장
	 * 
	 * @param userInfoForm 입럭할 사용자 정보 (현재기준 Email)
	 * @return 정상저장의 경우 ID값(양의 정수) , 아닌경우 -1
	 */
	public int saveNewsLong(NewsLong newsLong) {
		
		newsLong = newsLongRepo.save(newsLong);
		if (newsLong.getId() != null) {
			log.error("saveNewsLong Save Error. {}", newsLong);
			return -1;
		}
		
		return newsLong.getId();
	}
	
	/**
	 * 긴소식 불러오기 (리스트)
	 */
	public Page<NewsLong> getNewsLongList(Pageable page) {
		Page<NewsLong> newsLongtList = newsLongRepo.findAll(page);
		return newsLongtList;
	}
}
