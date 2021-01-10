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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "lunch")
public class Lunch implements Serializable {
	
	private static final long serialVersionUID = 5759159601176517364L;
	
	/**
	 * 자동생성 primary Key
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
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
	 * 포함된 이미지 리스트
	 */
	@OneToMany
	@JoinColumn(name="group_no", referencedColumnName="id")
	private List<Photo> photoList;
}
