package com.twg0.myacademy.domain.common.exception.invalidvalue;

import com.twg0.myacademy.domain.common.exception.ErrorCode;

public class DuplicatedException extends InvalidValueException{
	public DuplicatedException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public DuplicatedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
