package com.tuozhi.zhlw.common.base.mymapper;

public class CdError {
	
	private int errorCode;
	private String message;
	private boolean success;
	private Exception exception;

	public CdError() {
		this.setSuccess(true);
		this.setErrorCode(CdMessageCode.Success);
	}

	public void setMessage(String message) {
		this.message = message;		
	}
	public String getMessage() {
		return message;		
	}
	

	

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		this.message= CdMessageUtil.readDate(String.valueOf(errorCode));		
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
