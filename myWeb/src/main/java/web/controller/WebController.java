package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.NewsShort;
import web.service.NewsService;

@Slf4j
@Controller
public class WebController {
	
	@Autowired
	NewsService newsService;
	
	
	@GetMapping("/")
	public String index(Model model) {
		
		// 짧은뉴스 목록
		List<NewsShort> newsShortList = newsService.getNewsShortList();
		model.addAttribute("newsShortList", newsShortList);
		
		log.info("newsShortList :: {}", newsShortList.toString());

		return "index";
	}

	@GetMapping("/shortNews")
	public String shortNews() {
		return "shortNews";
	}
	
	@GetMapping("/longNews")
	public String longNews() {
		return "longNews";
	}
	
	@GetMapping("/lunch")
	public String lunch() {
		return "lunch";
	}
	
	@GetMapping("/garbage")
	public String garbage() {
		return "garbage";
	}
	
	@GetMapping("/subscribe")
	public String subscribe() {
		return "subscribe";
	}
	
	@GetMapping("/privacy")
	public String privacy() {
		return "privacy";
	}
	
	@GetMapping("/termsOfUse")
	public String termsOfUse() {
		return "termsOfUse";
	}
}
