package web.data.repogitory;

import org.springframework.data.jpa.repository.JpaRepository;

import web.data.entity.UserInfo;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer>{
	
	/*
	 * 이메일에 해당하는 고객정보 get
	 */
	UserInfo findByEmail(String email);
	
	/*
	 * 닉네임에 해당하는 고객정보 get
	 */
	UserInfo findByNickname(String nickname);
	
	/**
	 * 이메일에 해당하는 고객정보 삭제
	 * @param email
	 */
	void deleteByEmail(String email);
}
