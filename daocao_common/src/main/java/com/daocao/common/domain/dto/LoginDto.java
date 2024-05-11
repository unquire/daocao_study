package com.daocao.common.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.dto
 * Project:daocao_study
 * name:LoginDto
 * Date:2024/3/16 22:13
 */
// 数据传输对象，在程序中各层传输
@Data
public class LoginDto implements Serializable {
	private String account;
	private String password;
	private Integer rememberMe;
}
