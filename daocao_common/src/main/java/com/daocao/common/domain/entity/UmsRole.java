package com.daocao.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.entity
 * Project:daocao_study
 * name:UmsRole
 * Date:2024/3/13 10:09
 */
@Data
@TableName("ums_role")
public class UmsRole implements Serializable {
	@TableId
	private Long roleId;
	private String roleLabel;
	private String roleName;
	private Integer sort;
	private Integer status;
	private String creator;
	private String updater;
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
	private String remark;
	// 逻辑删除，MyBatis-PLus默认0是未册除，1是已删除
	@TableLogic
	private Integer deleted;
}
