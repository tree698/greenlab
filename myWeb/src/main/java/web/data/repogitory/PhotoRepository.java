package web.data.repogitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import web.data.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
	
	public Page<Photo> findByPhotoType(String photoType, Pageable page);
}
