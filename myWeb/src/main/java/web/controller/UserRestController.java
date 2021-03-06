package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.UserInfo;
import web.model.ApiResult;
import web.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 회원가입
	 * 
	 * @param userInfo
	 * @return
	 */
	@PostMapping(path = {"register"})
	public ApiResult registerUser(String email, String nickname, String password, String sex, Integer age) {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email);
		userInfo.setNickname(nickname);
		userInfo.setPassword(password);
		userInfo.setSex(sex);
		userInfo.setAge(age);
		
		int userId = userService.joinUserInfo(userInfo);
		if (userId < 0) {
			log.error("user join fail.. {}", userInfo);
			return new ApiResult(ApiResult.RET_FAIL_CODE);
		}
		return new ApiResult(ApiResult.RET_SUCCESS_CODE);
	}
	
	/**
	 * 탈퇴
	 * 
	 * @param userInfo
	 * @return
	 */
	@PostMapping(path = {"secession"})
	public ApiResult secession(String email, String reason) {
		int ret = userService.removeUserByEmail(email, reason);
		
		if (ret == 1) {
			return new ApiResult(ApiResult.RET_SUCCESS_CODE);
		}
		return  new ApiResult(ApiResult.RET_FAIL_CODE, "가입된 이메일이 아닙니다.");
	}
	
	/**
	 * 중복확인
	 * @param userInfo
	 * @return
	 */
	@PostMapping(path = {"dupleracate"})
	public ApiResult dupleracate(String nickname, String email) {
		
		boolean emailAlready = userService.checkAlreadyUserEmail(email);
		if (emailAlready) {
			return new ApiResult(ApiResult.RET_FAIL_CODE, "이미 가입된 이메일 입니다");
		}
		
		boolean nickAlready = userService.checkAlreadyUserNickname(nickname);
		if (nickAlready) {
			return new ApiResult(ApiResult.RET_FAIL_CODE, "이미 가입된 닉네임 입니다");
		}
		
		return new ApiResult(ApiResult.RET_SUCCESS_CODE);
	}
}
