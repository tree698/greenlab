package web.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table(name="user_info")
public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = 3826076993492916439L;

	/**
	 * 자동생성 primary Key
	 */
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 고객Email
	 */
	@Column(name="email", length=80, unique=true)
	private String email;
	
	/**
	 * Nickname
	 */
	@Column(name="nickname", length=80)
	private String nickname;
	
	/**
	 * 성별 (m-남, w-여)
	 */
	@Column(name="sex", length=1)
	private String sex;
	
	/**
	 * 연령대
	 */
	@Column(name="age")
	private int age;
	
	
	/**
	 * 생성일
	 */
	@Column(name="created")
	@CreatedDate
	private LocalDateTime created;
	
	/**
	 * 수정일
	 */
	@Column(name="modified")
	@UpdateTimestamp
	private LocalDateTime modified;
}
