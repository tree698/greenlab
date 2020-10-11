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

/**
 * 뉴스의 출처 테이블
 * @author WOOK
 *
 */
@Data
@Entity
@Table(name="news_source")
public class NewsSource implements Serializable {
	
	private static final long serialVersionUID = 4485565946903550228L;
	
	/**
	 * 자동생성 primary Key
	 */
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 연계된 뉴스의 ID
	 */
	@Column(name="news_id", nullable = false)
	private Integer newsId;
	
	/**
	 * 연계된 뉴스의 소스 순위 (높은 순위 일수록 작은 값)
	 */
	@Column(name="sort")
	private Integer sort;
	
	/**
	 * 소스 정보
	 */
	@Column(name="title", length=400, nullable = false)
	private String title;
	
	/**
	 * 출처
	 */
	@Column(name="link", length=400)
	private String link;
	
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
