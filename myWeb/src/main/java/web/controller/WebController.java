package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.NewsShort;
import web.service.NewsService;

@Slf4j
@Controller
@RequestMapping("/")
public class WebController {
	
	@GetMapping("/")
	public String index(Model model) {
		return "index";
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
