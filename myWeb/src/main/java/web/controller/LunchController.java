package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.Lunch;
import web.service.PhotoService;

@Slf4j
@Controller
@RequestMapping("/lunch")
public class LunchController {
	
	@Autowired
	PhotoService imageGarbageService;
	
	@GetMapping("")
	public String shortNews(Model model, @PageableDefault(sort={"created"}, direction=Direction.DESC, size = 18)Pageable page,
			@RequestParam(required=false) Integer photoId) {
		
		// 점심사진 목록
		// Page<Photo> lunchList = imageGarbageService.getImageGarbageList("L", page);
		Page<Lunch> lunchList = imageGarbageService.getLunchList(page);
		
		model.addAttribute("lunchList", lunchList);
		
		// 특정 사진 바로 띄울경우
		model.addAttribute("photoId", photoId);
		
		return "lunch";
	}
}
