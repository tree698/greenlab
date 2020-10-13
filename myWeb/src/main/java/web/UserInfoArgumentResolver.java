package web;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import web.data.entity.UserInfo;

public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return UserInfo.class.isAssignableFrom(parameter.getParameterType());
	}

	/**
	 * 세션에 담긴 유저정보
	 * 로그인 상태가 아닐 경우, Contoller 에 null 전달
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserInfo) {
			return principal;
		}
		return null;
	}

}
