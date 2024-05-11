package com.daocao.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daocao.common.domain.entity.UmsMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.mapper
 * Project:daocao_study
 * name:UmsMenuMapper
 * Date:2024/3/13 10:28
 */
@Mapper
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

	List<UmsMenu> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
}
