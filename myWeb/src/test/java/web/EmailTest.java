package web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.News;
import web.service.EmailService;
import web.service.NewsService;

@SpringBootTest
@Slf4j
class EmailTest {

	@Autowired
	EmailService emailService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void emailTest() {
		emailService.weeklyMailBuild("socerlike@hanmail.net", "test");
	}

}
