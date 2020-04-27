package com.yunyuan.searanch.utils;

import lombok.Data;

/**
 * @author alan
 * @date 2020/3/18
 * @param <T>
 */
@Data
public class ResponseData<T> {
	
	public static final String ERRORS_KEY = "errors";
	public static final String OK_KEY="ok";

	private String message;
	private int code;
	private T data;

	public ResponseData putDataValue(T value) {
		this.setData(value);
		return this;
	}
	
	public ResponseData(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ResponseData ok() {
		return new ResponseData(200, "Ok");
	}
	
	public static ResponseData notFound() {
		return new ResponseData(404, "Not Found");
	}
	
	public static ResponseData badRequest() {
		return new ResponseData(400, "Bad Request");
	}
	
	public static ResponseData forbidden() {
		return new ResponseData(403, "Forbidden");
	}
	
	public static ResponseData unauthorized() {
		return new ResponseData(401, "unauthorized");
	}
	
	public static ResponseData serverInternalError() {
		return new ResponseData(500, "Server Internal Error");
	}
	
	public static ResponseData error() {
		return new ResponseData(1001, "Customer Error");
	}
}
