package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableSpringDataWebSupport
public class GreenLab9Configuration implements WebMvcConfigurer {


	@Autowired
	HandlerInterceptorEx handlerInterceptorEx;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(handlerInterceptorEx).addPathPatterns("/*");
	}
}
