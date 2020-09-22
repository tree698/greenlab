package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.NewsLong;
import web.data.entity.NewsShort;
import web.service.NewsService;

@Slf4j
@Controller
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	NewsService newsService;
	
	@GetMapping("/short")
	public String shortNews(Model model, Pageable page) {
		
		// 짧은뉴스 목록
		Page<NewsShort> newsShortList = newsService.getNewsShortList(page);
		model.addAttribute("newsShortList", newsShortList.getContent());
		
		log.info("newsShortList :: {}", newsShortList.toString());
		return "shortNews";
	}
	
	@GetMapping("/long")
	public String longNews(Model model, Pageable page) {
		
		// 긴 뉴스 목록
		Page<NewsLong> newsLongtList = newsService.getNewsLongList(page);
		model.addAttribute("newsLongList", newsLongtList.getContent());
		
		log.info("newsShortList :: {}", newsLongtList.toString());
		return "longNews";
	}
}
