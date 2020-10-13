package web.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the EstmateInfo database table.
 * 
 */

@NoArgsConstructor
@Entity
@Data
public class WishNews implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private WishNewsPK id;
	
	/**
	 * 생성일
	 */
	@Column(name="created")
	@CreatedDate
	private LocalDateTime created;

	public WishNews(int newsId, int userId, LocalDateTime created) {
		this.id = new WishNewsPK(newsId, userId);
		this.created = created;
	}
}