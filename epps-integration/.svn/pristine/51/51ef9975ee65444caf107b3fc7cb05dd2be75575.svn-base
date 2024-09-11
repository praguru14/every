package com.epps.framework.domain.exception;

import com.epps.framework.application.util.logger.ILogOnce;

public class ValidationException extends RuntimeException implements ILogOnce{
	
	private static final long serialVersionUID = 4265758284484258031L;

	public static final String UNHANDLED_EXCEPTION_TXT = "Unhandled Application Exception !!! ";
	
	private String message;
	private boolean logged;
	private  ErrorCode errorCode;
	private  ResponseInfoType errorType;
	
	public ValidationException(final String message, final ErrorCode code, ResponseInfoType errorType) {
		this.setMessage(message);
		this.errorCode = code;
		this.errorType = errorType;
	}

	public ValidationException(final String message, final ErrorCode code, final Throwable throwable, ResponseInfoType errorType) {
		super(throwable);
		this.errorCode = code;
		this.setMessage(message);
		this.errorType = errorType;
		
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public boolean isLogged() {
		return this.logged;
	}
	
	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseInfoType getErrorType() {
		return errorType;
	}	

	

}
