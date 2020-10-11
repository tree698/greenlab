package web.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.News;
import web.data.entity.WishNews;
import web.data.repogitory.NewsRepo;
import web.data.repogitory.WishNewsRepo;

/**
 * 고객정보 관련 Serivce
 */
@Slf4j
@Service
public class NewsService {
	
	/**
	 * 짧은소식 Data Control
	 */
	@Autowired
	NewsRepo newsRepo;
	
	@Autowired
	WishNewsRepo wishNewsRepo;
	
	
	/**
	 * 짧은소식 저장
	 * 
	 * @param userInfoForm 입럭할 사용자 정보 (현재기준 Email)
	 * @return 정상저장의 경우 ID값(양의 정수) , 아닌경우 -1
	 */
	public int saveNewsShort(News newsShort) {
		
		newsShort = newsRepo.save(newsShort);
		if (newsShort.getId() == null) {
			log.error("saveNewsShort Save Error. {}", newsShort);
			return -1;
		}
		
		return newsShort.getId();
	}
	
	/**
	 * 짧은소식 불러오기 (리스트)
	 */
	public Page<News> getNewsShortList(Pageable page) {
		Page<News> newsList = newsRepo.findByNewsType(News.NEWS_TYPE_SHORT, page);
		return newsList;
	}
	
	/**
	 * 뉴스 한 개 불러오기
	 */
	public News getNews(Integer id, String newsType) {
		News news = newsRepo.findByIdAndNewsType(id, newsType);
		return news;
	}
	
	/**
	 * 긴소식 저장
	 * 
	 * @param userInfoForm 입럭할 사용자 정보 (현재기준 Email)
	 * @return 정상저장의 경우 ID값(양의 정수) , 아닌경우 -1
	 */
	public int saveNewsLong(News newsLong) {
		
		newsLong = newsRepo.save(newsLong);
		if (newsLong.getId() != null) {
			log.error("saveNewsLong Save Error. {}", newsLong);
			return -1;
		}
		
		return newsLong.getId();
	}
	
	/**
	 * 긴소식 불러오기 (리스트)
	 */
	public Page<News> getNewsLongList(Pageable page) {
		Page<News> newsList = newsRepo.findByNewsType(News.NEWS_TYPE_LONG, page);
		return newsList;
	}
	
	
	/**
	 * 뉴스 찜하기 추가
	 */
	public void saveWishNews(int newsId, int userId) {
		WishNews wishNews = new WishNews(newsId, userId, LocalDateTime.now());
		wishNewsRepo.save(wishNews);
	}
}
