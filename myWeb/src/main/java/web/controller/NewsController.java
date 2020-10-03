package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.News;
import web.service.NewsService;

@Slf4j
@Controller
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	NewsService newsService;
	
	/**
	 * 짧은뉴스 목록 페이지
	 */
	@GetMapping("/short")
	public String shortNews(Model model, Pageable page) {
		
		// 짧은뉴스 목록
		Page<News> newsShortList = newsService.getNewsShortList(page);
		model.addAttribute("newsShortList", newsShortList);
		
		log.info("newsShortList :: {}", newsShortList.toString());
		return "shortNews";
	}
	
	/**
	 * 짧은뉴스 modal > iframe 용 페이지
	 */
	@GetMapping("/short/modal")
	public String shortNews(Model model, Integer newsId) {
		
		// 짧은뉴스 목록
		News news = newsService.getNews(newsId, News.NEWS_TYPE_SHORT);
		model.addAttribute("news", news);
		
		return "shortNewsModal";
	}
	
	
	/**
	 * 긴 뉴스 목록 페이지
	 */
	@GetMapping("/long")
	public String longNews(Model model, Pageable page) {
		
		// 긴 뉴스 목록
		Page<News> newsLongList = newsService.getNewsLongList(page);
		model.addAttribute("newsLongList", newsLongList);
		
		log.info("newsLongList :: {}", newsLongList.toString());
		return "longNews";
	}
	
	/**
	 * 긴뉴스 modal > iframe 용 페이지
	 */
	@GetMapping("/long/modal")
	public String longNews(Model model, Integer newsId) {
		
		// 짧은뉴스 목록
		News news = newsService.getNews(newsId, News.NEWS_TYPE_LONG);
		model.addAttribute("news", news);
		
		return "longNewsModal";
	}
}
