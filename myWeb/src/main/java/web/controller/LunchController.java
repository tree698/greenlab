package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.ImageGarbage;
import web.service.ImageGarbageService;

@Slf4j
@Controller
@RequestMapping("/lunch")
public class LunchController {
	
	@Autowired
	ImageGarbageService imageGarbageService;
	
	@GetMapping("")
	public String shortNews(Model model, @PageableDefault(size=12, sort = {"id"}, direction = Sort.Direction.ASC)Pageable page) {
		
		// 점심사진 목록
		Page<ImageGarbage> lunchList = imageGarbageService.getImageGarbageList("L", page);
		model.addAttribute("lunchList", lunchList);
		
		return "lunch";
	}
}
