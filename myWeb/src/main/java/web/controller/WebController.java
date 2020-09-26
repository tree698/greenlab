package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

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
