package com.my.web.exception;
/**
 * @description 自定义异常
 * @author guilin
 *
 */
public class CustomException extends Exception {

	private String message;
	
	//错误代号 100 需要登录 101返回新的token
	private int errorCode;
	
	public CustomException(String message) {
		super(message);
		this.message=message;
	}
	
	public CustomException(String message, int errorCode) {
		super(message);
		this.message = message;
		this.errorCode = errorCode;
	}




	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
