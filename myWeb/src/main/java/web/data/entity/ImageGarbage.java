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
@Table(name = "image_garbage")
public class ImageGarbage implements Auditable<String, Long, LocalDateTime>, Serializable {

	@GeneratedValue
	@Id
	@ToString.Include
	@EqualsAndHashCode.Include
	@Column(name = "id")
	private Long id;

	@ToString.Include
	@Column(name = "path")
	private String path;

	@ToString.Include
	@Column(name = "filename")
	private String filename;

	@ToString.Include
	@Column(name = "title")
	private String title;
	
	@ToString.Include
	@Column(name = "location")
	private String location;

	@ToString.Include
	@Column(name = "original_filename")
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
