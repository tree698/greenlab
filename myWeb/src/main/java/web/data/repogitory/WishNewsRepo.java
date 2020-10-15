package web.data.repogitory;

import org.springframework.data.jpa.repository.JpaRepository;

import web.data.entity.WishNews;
import web.data.entity.WishNewsPK;

public interface WishNewsRepo extends JpaRepository<WishNews, WishNewsPK>{
	
	int findCountByIdNewsId(Integer newsId);
}
