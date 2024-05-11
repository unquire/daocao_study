package com.daocao.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.entity
 * Project:daocao_study
 * name:UmsSysUser
 * Date:2024/3/11 20:33
 */
@Data
@TableName("ums_sys_user")
public class UmsSysUser implements Serializable {
	@TableId
	private Long id;
	private String username;
	private String nickname;
	private String email;
	private String mobile;
	// 用 Integer
	private Integer sex;
	private String avatar;
	private String password;
	private Integer status ;
	private String creator;
	private String updater;
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
	private String remark;
	// 逻辑删除，Mybatis-Plus默认为0是未删除，1是已删除
	@TableLogic
	private Integer deleted;

	// 角色信息
	@TableField(exist = false)
	private List<UmsRole> roleList = new ArrayList<>();
	// 权限信息
	@TableField(exist = false)
	private List<String> perms = new ArrayList<>();
}
