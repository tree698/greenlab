package web.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 뉴스의 카테고리
 * @author WOOK
 *
 */
@Data
@Entity
@Table(name="news_category")
public class NewsCategory implements Serializable {
	
	private static final long serialVersionUID = -5100388051169150662L;

	/**
	 * 자동생성 primary Key
	 */
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 카테고리 명
	 */
	@Column(name="name", nullable = false)
	private String name;

}
