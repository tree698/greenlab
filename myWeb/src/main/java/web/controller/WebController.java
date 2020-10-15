package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class WebController {
	
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	/**
	 * 회원가입
	 * @return
	 */
	@GetMapping("/subscribe")
	public String subscribe() {
		return "subscribe";
	}
	
	/**
	 * 회원탈퇴
	 * @return
	 */
	@GetMapping("/unsubscribe")
	public String unsubscribe(@RequestParam(required=false) String email, Model model) {
		model.addAttribute("email", email);
		return "unsubscribe";
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
