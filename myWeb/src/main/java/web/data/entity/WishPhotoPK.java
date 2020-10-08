package web.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EstmateInfo database table.
 * 
 */
@Embeddable
public class WishPhotoPK implements Serializable {

	@Column(name="photo_id")
	private Integer photoId;

	@Column(name="user_id")
	private Integer userId;

}