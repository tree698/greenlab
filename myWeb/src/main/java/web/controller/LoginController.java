package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import web.service.UserService;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 회원가입
	 * 
	 * @param userInfo
	 * @return
	 */
	@GetMapping({"", "/"})
	public String login() {
		log.info("login");
		
		return "login";
	}
}
