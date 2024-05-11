package com.daocao.support.filter;

import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.Service.utils.JwtUtils;
import com.daocao.common.domain.vo.LoginUserVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Author:啦啦啦
 * Package:com.daocao.support.filter
 * Project:daocao_study
 * name:JwtAuthticationFilter
 * Date:2024/3/20 19:51
 */
@Component
@Slf4j
public class JwtAuthticationFilter extends OncePerRequestFilter {
	private final JwtUtils jwtUtils;

	public JwtAuthticationFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 	获取登录的用户信息
		LoginUserVO loginUserVO = (LoginUserVO) jwtUtils.getLoginUser(request);
		if(ObjectUtil.isNotNull(loginUserVO)){
			// 鉴权,识别用户是否有访问权限
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(loginUserVO, null, loginUserVO.getAuthorities());
			// 将用户信息存储到SecurityContext中，SecurityContext存储到SecurityContextHolder中
			// SecurityContext是当前线程的用户，SecurityContextHolder是SecurityContext的工具类
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		// 放行，交由后面的过滤器执行，如果没有登录，就会被登录拦截器[UsernamePasswordAuthticationFilter]拦截到
		// /auth/sys接口不需要任何权限
		filterChain.doFilter(request,response);
	}
}
