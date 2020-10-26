package web.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.Secession;
import web.data.entity.UserInfo;
import web.data.repogitory.SecessionRepo;
import web.data.repogitory.UserInfoRepo;

/**
 * 고객 정보 서비스
 */
@Slf4j
@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserInfoRepo userInfoRepo;
	
	@Autowired
	SecessionRepo secessionRepo;
	
	/**
	 * 고객 정보를 등록한다. (회원가입)
	 * 
	 * @param userInfoForm
	 * @return 유저희 DB 저장 ID값
	 */
	public int joinUserInfo(UserInfo userInfoForm) {
		
		// password 암호화
		String encPassword = new BCryptPasswordEncoder().encode(userInfoForm.getPassword());
		userInfoForm.setPassword(encPassword);
		
		userInfoForm.setCreated(LocalDateTime.now());
		userInfoForm = userInfoRepo.save(userInfoForm);
		if (userInfoForm.getId() == null) {
			log.error("joinUserInfo Save Error. {}", userInfoForm);
			return -1;
		}
		
		return userInfoForm.getId();
	}
	
	/**
	 * 고객 정보를 삭제 (탈퇴)
	 * @return 삭제된 계정수 - 정상일 경우 1
	 */
	public int removeUserByEmail(String email, String reason) {
		
		UserInfo user = userInfoRepo.findByEmail(email);
		if (user == null) {
			return 0;
		}
		
		Secession secession = new Secession();
		secession.setCreated(user.getCreated());
		secession.setEndDate(LocalDateTime.now());
		secession.setReason(reason);
		
		// 삭제
		userInfoRepo.deleteById(user.getId());
		// 삭제 리스트 추가
		secessionRepo.save(secession);
		
		return 1;
	}
	
	
	/**
	 * 이메일 중복확인
	 * 
	 * @return 이미 가입된 이베일이면 true, 사용가능 이메일이면 false
	 * 
	 */
	public boolean checkAlreadyUserEmail(String email) {
		
		UserInfo user = userInfoRepo.findByEmail(email);
		if (user != null) {
			log.info("Alraey join email  {}", email);
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 이메일 중복확인
	 * 
	 * @return 이미 가입된 이베일이면 true, 사용가능 이메일이면 false
	 * 
	 */
	public boolean checkAlreadyUserNickname(String nickname) {
		
		UserInfo user = userInfoRepo.findByNickname(nickname);
		if (user != null) {
			log.info("Alraey join nickname  {}", nickname);
			return true;
		}
		
		return false;
	}

	@Override
	public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
		return userInfoRepo.findByEmail(username);
	}
}
