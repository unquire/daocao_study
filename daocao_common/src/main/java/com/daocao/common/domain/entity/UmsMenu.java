package com.daocao.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.entity
 * Project:daocao_study
 * name:UmsMenu
 * Date:2024/3/13 10:18
 */
@Data
@TableName("ums_menu")
public class UmsMenu implements Serializable {
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long parentId;
	@NotNull(message = "请填写菜单名")
	private String menuName;
	private String path;
	private String componentPath;
	@NotNull(message = "权限名不为空")
	private String perms;
	@NotNull(message = "icon不为空")
	private String icon;
	@NotNull(message = "请选择菜单类型")
	private Integer menuType ;
	private Integer sort;
	private Integer status;
	private String creator;
	private String updater;
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;
	private String remark;
	// 逻辑删除，MyBatis-PLus默认0是未删除，1是已删除
	@TableLogic
	private Integer deleted;


}
