package com.daocao.auth.controller;

import com.daocao.common.Service.IUmsSysUserService;
import com.daocao.common.domain.entity.UmsSysUser;
import com.daocao.common.response.DaoCaoResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.controller
 * Project:daocao_study
 * name:UmsSysUserController
 * Date:2024/3/14 21:57
 */
@RestController
@RequestMapping("ums/sysuser")
public class UmsSysUserController {
	private final IUmsSysUserService sysUserService;

	public UmsSysUserController(IUmsSysUserService iUmsSysUserService) {
		this.sysUserService = iUmsSysUserService;
	}

	/*
	 * 新增用户接口
	 * */
	@PostMapping
	public DaoCaoResult addSysUser(@RequestBody UmsSysUser user) {
		boolean save = sysUserService.save(user);
		if(save){
			return DaoCaoResult.success();
		}
		return DaoCaoResult.error();
	}
	/*
	* 查询用户接口
	* */
	@GetMapping
	public DaoCaoResult selectSysUser(){
		List<UmsSysUser> list = sysUserService.list();
		list.forEach(System.out::println);
		return DaoCaoResult.success(list);
	}
}
