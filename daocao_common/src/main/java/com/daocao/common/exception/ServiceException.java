package com.daocao.common.exception;
import com.daocao.common.constants.HttpStatus;

/**
 * Author:啦啦啦
 * Package:com.daocao.common.exception
 * Project:daocao_study
 * name:ServiceException
 * Date:2024/3/13 21:56
 */
public class ServiceException extends  RuntimeException{
	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private Integer code;

	/**
	 * 错误提示
	 */
	private String message;

	/**
	 * 错误明细，内部调试错误
	 */
	private String detailMessage;

	/**
	 * 空构造方法，避免反序列化问题
	 */
	public ServiceException() {
	}

	public ServiceException(String message) {
		this.message = message;
	}

	public ServiceException(Integer code,String message) {
		this.message = message;
		this.code = code;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}

	public ServiceException setMessage(String message) {
		this.message = message;
		return this;
	}

}
