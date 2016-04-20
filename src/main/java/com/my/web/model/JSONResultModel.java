package com.my.web.model;

import java.io.Serializable;
/**
 * @description JSON请求返回的包装类
 * @author guilin
 * @param <T>
 */
public class JSONResultModel<T>implements Serializable{
	/**请求是否成功**/
	private boolean success;
	/**错误码**/
	private int errorCode;
	/**错误信息**/
	private String message;
	/**请求成功的内容体**/
	private T entity;
	
	public JSONResultModel(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public JSONResultModel(boolean success, T entity) {
		super();
		this.success = success;
		this.entity = entity;
	}
	public JSONResultModel(boolean success, int errorCode, String message) {
		super();
		this.success = success;
		this.errorCode = errorCode;
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	
	

}
