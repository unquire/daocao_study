package com.daocao.common.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author:啦啦啦
 * Package:com.daocao.common.domain.vo
 * Project:daocao_study
 * name:UserInfoVO
 * Date:2024/3/27 21:09
 */
@Data
public class UserInfoVO implements Serializable {
	private Long id;
	private String nickname;
	private String avatar;
}
