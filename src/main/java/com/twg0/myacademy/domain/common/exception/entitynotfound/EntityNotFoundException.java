package com.twg0.myacademy.domain.common.exception.entitynotfound;

import com.twg0.myacademy.domain.common.exception.BusinessException;
import com.twg0.myacademy.domain.common.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
