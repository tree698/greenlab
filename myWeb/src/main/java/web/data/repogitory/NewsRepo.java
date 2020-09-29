package web.data.repogitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import web.data.entity.News;

public interface NewsRepo extends JpaRepository<News, Integer>{
	
	/**
	 * 뉴스 리스트 불러오기 (타입에 따라)
	 */
	public Page<News> findByNewsType(String type, Pageable page);
	
	/**
	 * 뉴스 리스트 불러오기 (id, type 에 해당하는 하나)
	 */
	public News findByIdAndNewsType(Integer id, String type);
}
