package com.daocao.sysuser;

import com.daocao.common.Service.IUmsSysUserService;
import com.daocao.common.domain.vo.UserInfoVO;
import com.daocao.common.response.DaoCaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:啦啦啦
 * Package:com.daocao.sysuser
 * Project:daocao_study
 * name:UserController
 * Date:2024/3/27 21:15
 */
@RestController
@Slf4j
@RequestMapping("sys/user")
public class UserController {
	private final IUmsSysUserService sysuserService;

	public UserController(IUmsSysUserService sysuserService) {
		this.sysuserService = sysuserService;
	}
	@GetMapping("/info")
	public DaoCaoResult searchUserInfoVO(){
		UserInfoVO userInfoVO = sysuserService.serchUserInfoVO();
		log.info("userInfoVO:"+userInfoVO);
		return DaoCaoResult.success(userInfoVO);
	}
}
