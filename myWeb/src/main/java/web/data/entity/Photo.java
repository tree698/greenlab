package web.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "photo")
public class Photo implements Auditable<String, Long, LocalDateTime>, Serializable {

	private static final long serialVersionUID = 5759659143276517364L;

	@GeneratedValue
	@Id
	@ToString.Include
	@EqualsAndHashCode.Include
	@Column(name = "id")
	private Long id;

	/**
	 * 이미지 타입
	 * G - 쓰레기 사진, L - 점심사진
	 */
	@Column(name="photo_type", length=1, nullable = false)
	private String photoType;
	
	@ToString.Include
	@Column(name = "path", length = 400)
	private String path;

	@ToString.Include
	@Column(name = "filename", length = 200)
	private String filename;
	
	/**
	 * 이미지 제목
	 */
	@ToString.Include
	@Column(name = "title", length = 200)
	private String title;
	
	/**
	 * 촬영 위치
	 */
	@ToString.Include
	@Column(name = "location", length = 200)
	private String location;
	
	/**
	 * 촬영 위치 상세 (가게명 등)
	 */
	@ToString.Include
	@Column(name = "place", length = 200)
	private String place;
	
	/**
	 * 코멘트
	 */
	@ToString.Include
	@Column(name = "comment", length = 400)
	private String comment;

	@ToString.Include
	@Column(name = "original_filename", length = 200)
	private String originalFilename;

	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

	@LastModifiedBy
	@Column(name = "last_modified_by")
	private String lastModifiedBy;

	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate;

	@Override
	public Optional<String> getCreatedBy() {
		return Optional.ofNullable(createdBy);
	}

	@Override
	public Optional<LocalDateTime> getCreatedDate() {
		return Optional.ofNullable(createdDate);
	}

	@Override
	public Optional<String> getLastModifiedBy() {
		return Optional.ofNullable(lastModifiedBy);
	}

	@Override
	public Optional<LocalDateTime> getLastModifiedDate() {
		return Optional.ofNullable(lastModifiedDate);
	}

	@Override
	public boolean isNew() {
		return getId() == null;
	}
}
