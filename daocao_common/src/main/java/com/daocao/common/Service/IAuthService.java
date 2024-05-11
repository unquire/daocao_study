package com.daocao.common.Service;

import com.daocao.common.domain.dto.LoginDto;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.Service
 * Project:daocao_study
 * name:IAuthService
 * Date:2024/3/17 14:25
 */
public interface IAuthService {
	String login(LoginDto loginDto);
}
