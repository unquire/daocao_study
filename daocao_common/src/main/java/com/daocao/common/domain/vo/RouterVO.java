package com.daocao.common.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.daocao.common.domain.entity.UmsMenu;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:啦啦啦
 * Package:com.daocao.common.domain.vo
 * Project:daocao_study
 * name:RouterVO
 * Date:2024/3/21 17:34
 */
@Data
public class RouterVO implements Serializable {
	private Long id;
	private Long parentId;
	private String menuName;
	private String path;
	private String perms;
	private String componentPath;
	private String icon;
	private Integer menuType; // 0是目录，1是菜单，2是按钮
	private String remark;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;
	// 存放子路由
	private List<RouterVO> children = new ArrayList<>();
}
