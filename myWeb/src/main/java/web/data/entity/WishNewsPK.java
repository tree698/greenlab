package web.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;

/**
 * The primary key class for the EstmateInfo database table.
 * 
 */

@NoArgsConstructor
@Embeddable
public class WishNewsPK implements Serializable {

	private static final long serialVersionUID = -8470852191697131472L;

	@Column(name="news_id")
	private Integer newsId;

	@Column(name="user_id")
	private Integer userId;

	public WishNewsPK(Integer newsId, Integer userId) {
		super();
		this.newsId = newsId;
		this.userId = userId;
	}
}