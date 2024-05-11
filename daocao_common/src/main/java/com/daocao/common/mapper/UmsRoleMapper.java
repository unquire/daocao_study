package com.daocao.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daocao.common.domain.entity.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.mapper
 * Project:daocao_study
 * name:UmsRoleMapper
 * Date:2024/3/13 10:29
 */
@Mapper
public interface UmsRoleMapper extends BaseMapper<UmsRole> {
	List<Long> selectByUserId(@Param("userId") Long userId);
}
