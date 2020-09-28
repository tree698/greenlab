package web.data.repogitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import web.data.entity.ImageGarbage;

public interface ImageGarbageRepository extends JpaRepository<ImageGarbage, Long> {
	
	public Page<ImageGarbage> findByImageType(String imageType, Pageable page);
}
