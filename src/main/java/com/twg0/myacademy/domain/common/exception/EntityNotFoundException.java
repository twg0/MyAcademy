package com.twg0.myacademy.domain.common.exception;

public class EntityNotFoundException extends BusinessException{
	public EntityNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
