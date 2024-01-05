package com.twg0.myacademy.domain.common.exception.invalidvalue;

import com.twg0.myacademy.domain.common.exception.BusinessException;
import com.twg0.myacademy.domain.common.exception.ErrorCode;

public class InvalidValueException extends BusinessException {
	public InvalidValueException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public InvalidValueException(ErrorCode errorCode) {
		super(errorCode);
	}
}
