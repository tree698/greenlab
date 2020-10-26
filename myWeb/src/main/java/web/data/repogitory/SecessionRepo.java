package web.data.repogitory;

import org.springframework.data.jpa.repository.JpaRepository;

import web.data.entity.Secession;

public interface SecessionRepo extends JpaRepository<Secession, Integer>{
	
}
