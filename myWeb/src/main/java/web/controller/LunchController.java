package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.Photo;
import web.service.PhotoService;

@Slf4j
@Controller
@RequestMapping("/lunch")
public class LunchController {
	
	@Autowired
	PhotoService imageGarbageService;
	
	@GetMapping("")
	public String shortNews(Model model, @PageableDefault(sort={"created"}, direction=Direction.DESC, size = 12)Pageable page) {
		
		// 점심사진 목록
		Page<Photo> lunchList = imageGarbageService.getImageGarbageList("L", page);
		model.addAttribute("lunchList", lunchList);
		
		return "lunch";
	}
}
