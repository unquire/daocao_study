package com.daocao.common.Service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.Service.IAuthService;
import com.daocao.common.Service.utils.JwtUtils;
import com.daocao.common.constants.HttpStatus;
import com.daocao.common.domain.dto.LoginDto;
import com.daocao.common.domain.vo.LoginUserVO;
import com.daocao.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.rmi.server.ServerCloneException;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.Service.impl
 * Project:daocao_study
 * name:AuthServiceImpl
 * Date:2024/3/17 14:24
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	/*
	* login方法
	* */
	@Override
	public String login(LoginDto loginDto) {
		// 用于封装用户提供的账户信息和密码信息
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginDto.getAccount(), loginDto.getPassword());
		// 对用户的身份执行身份验证操作
		Authentication authenticate = authenticationManager.authenticate(authenticationToken);
		// 获取用户信息
		LoginUserVO loginUserVO = (LoginUserVO) authenticate.getPrincipal();
		// 根据loginUserV0创建token
		if(ObjectUtil.isNull(loginUserVO)){
			throw new ServiceException(HttpStatus.UNAUTHORIZED,"认证失败!");
		}
		//创建token
		String token = jwtUtils.createToken(loginUserVO);
		log.info("token====>"+token);
		return token;

	}
}
