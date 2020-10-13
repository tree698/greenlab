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
public class WishPhotoPK implements Serializable {

	@Column(name="photo_id")
	private Integer photoId;

	@Column(name="user_id")
	private Integer userId;
	
	public WishPhotoPK(Integer photoId, Integer userId) {
		super();
		this.photoId = photoId;
		this.userId = userId;
	}
}