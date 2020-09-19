package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class HandlerInterceptorEx implements HandlerInterceptor {
	
	@Value("${greenlab.res.url}")
	String resUrl;
	
	@Value("${greenlab.img.url}")
	String imgUrl;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (modelAndView != null) {
			// 모든 컨트롤러에서 공통으로 사용할 attribute 삽입
			modelAndView.addObject("resUrl", resUrl);
			modelAndView.addObject("imgUrl", imgUrl);
		}
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
}