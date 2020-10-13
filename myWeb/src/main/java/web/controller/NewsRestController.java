package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.News;
import web.data.entity.UserInfo;
import web.model.ApiResult;
import web.service.NewsService;

@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewsRestController {
	
	@Autowired
	NewsService newsService;
	
	/**
	 * 뉴스 Get
	 * @return
	 */
	@PostMapping(path = {"content"})
	public ApiResult content(String newsType, Integer newsId) {
		
		News news = newsService.getNews(newsId, newsType);
		
		return new ApiResult(ApiResult.RET_SUCCESS_CODE, null, news);
	}
	
	/**
	 * 찜하기
	 * @return
	 */
	@PostMapping(path = {"wish"})
	public ApiResult wish(UserInfo userInfo, String newsType, Integer newsId, boolean isAdd) {
		if (userInfo == null) {
			return new ApiResult(ApiResult.RET_FAIL_CODE, "로그인이 필요합니다");
		}
		if (isAdd) {
			newsService.saveWishNews(newsId, userInfo.getId());
		} else {
			newsService.deleteWishNews(newsId, userInfo.getId());
		}
		
		
		return new ApiResult(ApiResult.RET_SUCCESS_CODE);
	}
}
