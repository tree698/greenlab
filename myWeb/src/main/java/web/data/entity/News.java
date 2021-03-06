package web.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@Entity
@Table(name="news")
public class News implements Serializable {
	
	private static final long serialVersionUID = 3424178735440675007L;
	
	/**
	 * News Long type
	 */
	public static final String NEWS_TYPE_LONG = "L";
	
	/**
	 * News Short type
	 */
	public static final String NEWS_TYPE_SHORT = "S";
	
	/**
	 * 자동생성 primary Key
	 */
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 기사  타입
	 * S - Short(짧은뉴스) , L - Long(긴 뉴스)
	 */
	@Column(name="news_type", length=1, nullable = false)
	private String newsType;
	
	/**
	 * 기사 카테고리 정보
	 */
	@Column(name="category_id", nullable = false)
	private Integer categoryId;
	
	/**
	 * 기사 제목
	 */
	@Column(name="title", length=200, nullable = false)
	private String title;
	
	/**
	 * 기사 대표 이미지
	 */
	@Column(name="title_image", length=200)
	private String titleImage;
	
	/**
	 * 기사 대표 이미지 출
	 */
	@Column(name="title_image_source", length=200)
	private String titleImageSource;
	
	/**
	 * 기사 내용
	 */
	@Column(name="content", length=12000, nullable = false)
	private String content;
	
	/**
	 * 좋아오 갯수
	 */
	@Column(name="wish_count")
	private Integer wishCount;
	
	
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
	@LastModifiedDate
	private LocalDateTime modified;
	
	/**
	 * 뉴스 출처 리스트
	 */
	@OneToMany
	@JoinColumn(name="news_id", referencedColumnName="id")
	private List<NewsSource> newsSourceList;
	
	/**
	 * 카테고리
	 */
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="id", insertable = false, updatable = false)
	private NewsCategory newsCategory;
}
