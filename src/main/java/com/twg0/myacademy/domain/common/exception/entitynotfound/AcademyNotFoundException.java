package com.twg0.myacademy.domain.common.exception.entitynotfound;

import com.twg0.myacademy.domain.common.exception.ErrorCode;

public class AcademyNotFoundException extends EntityNotFoundException{

	public AcademyNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public AcademyNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
