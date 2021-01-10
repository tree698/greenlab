package web.data.repogitory;

import org.springframework.data.jpa.repository.JpaRepository;

import web.data.entity.Lunch;

public interface LunchRepo extends JpaRepository<Lunch, Integer>{
	
}
