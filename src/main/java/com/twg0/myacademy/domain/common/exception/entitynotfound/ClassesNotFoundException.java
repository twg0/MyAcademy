package com.twg0.myacademy.domain.common.exception.entitynotfound;

import com.twg0.myacademy.domain.common.exception.ErrorCode;

public class ClassesNotFoundException extends EntityNotFoundException{
	public ClassesNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public ClassesNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
