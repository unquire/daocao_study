package com.daocao.auth.controller;

import com.daocao.common.Service.IAuthService;
import com.daocao.common.domain.dto.LoginDto;
import com.daocao.common.response.DaoCaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.controller
 * Project:daocao_study
 * name:AuthController
 * Date:2024/3/16 22:12
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {
	private final IAuthService authService;

	public AuthController(IAuthService authService) {
		this.authService = authService;
	}

	/*
	* 用户系统登录
	* */
	@PostMapping("sys")
	public DaoCaoResult sysLogin(@RequestBody LoginDto loginDto){
		log.info("LoginDto=======>{}",loginDto);
		String token = authService.login(loginDto);
		return DaoCaoResult.success().put("token",token);
	}
}
