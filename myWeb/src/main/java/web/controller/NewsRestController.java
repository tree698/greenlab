package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.News;
import web.model.ApiResult;
import web.service.NewsService;

@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewsRestController {
	
	@Autowired
	NewsService newsService;
	
	/**
	 * 중복확인
	 * @param userInfo
	 * @return
	 */
	@PostMapping(path = {"content"})
	public ApiResult content(String newsType, Integer newsId) {
		
		News news = newsService.getNews(newsId, newsType);
		
		return new ApiResult(ApiResult.RET_SUCCESS_CODE, null, news);
	}
}
