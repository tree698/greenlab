package web.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;


/**
 * The persistent class for the EstmateInfo database table.
 * 
 */
@Entity
public class WishPhoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private WishPhotoPK id;
	
	/**
	 * 생성일
	 */
	@Column(name="created")
	@CreatedDate
	private LocalDateTime created;
}