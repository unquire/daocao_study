package com.daocao.common.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daocao.common.domain.entity.UmsSysUser;
import com.daocao.common.domain.vo.UserInfoVO;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.Service
 * Project:daocao_study
 * name:IUmsSysUserService
 * Date:2024/3/13 10:44
 */
public interface IUmsSysUserService extends IService<UmsSysUser> {
	 UserInfoVO serchUserInfoVO();
}
