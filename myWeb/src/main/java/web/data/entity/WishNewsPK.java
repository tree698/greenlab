package web.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EstmateInfo database table.
 * 
 */
@Embeddable
public class WishNewsPK implements Serializable {

	@Column(name="news_id")
	private Integer newsId;

	@Column(name="user_id")
	private Integer userId;

}