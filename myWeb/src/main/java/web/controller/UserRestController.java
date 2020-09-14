package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.UserInfo;
import web.model.ApiResult;
import web.service.UserSerivce;

@Slf4j
@RestController
public class UserRestController {
	
	@Autowired
	private UserSerivce userService;
	
	@PostMapping(path = {"/api/registUser"})
	public ApiResult registUser(UserInfo userInfo) {
		
		int userId = userService.joinUserInfo(userInfo);
		if (userId < 0) {
			log.error("user join fail.. {}", userInfo);
			return new ApiResult(ApiResult.RET_FAIL_CODE);
		}
		return new ApiResult(ApiResult.RET_SUCCESS_CODE);
	}
}
