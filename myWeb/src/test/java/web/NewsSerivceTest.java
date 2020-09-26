package web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import web.data.entity.News;
import web.service.NewsService;

@SpringBootTest
@Slf4j
class NewsSerivceTest {

	@Autowired
	NewsService newsService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void NewsShortSaveTest() {
		News tt = new News();
		tt.setTitle("테스트 뉴스 제목 입니다");
		tt.setContent("테스트 뉴스 내용 입니다.");
		int ret = newsService.saveNewsShort(tt);
		
		log.info("## NewsShortSaveTest : {}", ret);
	}

}
