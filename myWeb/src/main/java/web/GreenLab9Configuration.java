package web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST")
				.maxAge(3000);
	}
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		
		// 로그인 & 세션정보 리졸버 추가
		resolvers.add(new UserInfoArgumentResolver());
		
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}
	
	
}
