package com.daocao.common.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daocao.common.Service.IUmsSysUserService;
import com.daocao.common.Service.utils.security.DaoCaoSecurityUtil;
import com.daocao.common.domain.entity.UmsSysUser;
import com.daocao.common.domain.vo.UserInfoVO;
import com.daocao.common.mapper.UmsSysUserMapper;
import org.springframework.stereotype.Service;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.Service.impl
 * Project:daocao_study
 * name:UmsSysUserImpl
 * Date:2024/3/13 10:49
 */
@Service
public class UmsSysUserImpl extends ServiceImpl<UmsSysUserMapper, UmsSysUser> implements IUmsSysUserService {
	private final UmsSysUserMapper sysUserMapper;

	public UmsSysUserImpl(UmsSysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}
	@Override
	public UserInfoVO serchUserInfoVO() {
		Long userid = DaoCaoSecurityUtil.getUserId();
		UmsSysUser sysUser =  sysUserMapper.selectByUserId(userid);
		UserInfoVO userInfoVO = new UserInfoVO();
		// 第一个是原数据，第二个是准备copy过去的数据
		BeanUtil.copyProperties(sysUser,userInfoVO);
		return userInfoVO;
	}
}
