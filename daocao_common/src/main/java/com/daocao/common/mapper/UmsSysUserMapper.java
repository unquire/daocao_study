package com.daocao.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daocao.common.domain.entity.UmsSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.mapper
 * Project:daocao_study
 * name:UmsSysUserMapper
 * Date:2024/3/13 10:30
 */
@Mapper
public interface UmsSysUserMapper extends BaseMapper<UmsSysUser> {
	UmsSysUser selectUserByUserName(@Param("account") String account,@Param("accountType") int accountType);
	UmsSysUser selectByUserId(@Param("userid") Long userid);
}
