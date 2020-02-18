package com.qianfeng.openapi.sdk;

public class OpenApiException extends Exception {

	private static final long serialVersionUID = -7035498848577048685L;
	
	private String errCode;
	private String errMsg;

	public OpenApiException() {
		super();
	}

	public OpenApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public OpenApiException(String message) {
		super(message);
	}

	public OpenApiException(Throwable cause) {
		super(cause);
	}

	public OpenApiException(String errCode, String errMsg) {
		super(errCode + ": " + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

}
