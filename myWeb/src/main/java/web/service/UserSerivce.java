package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.UserInfo;
import web.data.repogitory.UserInfoRepo;

/**
 * 고객 정보 서비스
 */
@Slf4j
@Service
public class UserSerivce {
	
	@Autowired
	UserInfoRepo userInfoRepo;
	
	/**
	 * 고객 정보를 등록한다. (회원가입)
	 * 
	 * @param userInfoForm
	 * @return 유저희 DB 저장 ID값
	 */
	public int joinUserInfo(UserInfo userInfoForm) {
		
		userInfoForm = userInfoRepo.save(userInfoForm);
		if (userInfoForm.getId() != null) {
			log.error("joinUserInfo Save Error. {}", userInfoForm);
			return -1;
		}
		
		return userInfoForm.getId();
	}
}
