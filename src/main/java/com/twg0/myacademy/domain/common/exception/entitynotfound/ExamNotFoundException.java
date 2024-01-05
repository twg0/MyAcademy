package com.twg0.myacademy.domain.common.exception.entitynotfound;

import com.twg0.myacademy.domain.common.exception.ErrorCode;

public class ExamNotFoundException extends EntityNotFoundException{

	public ExamNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public ExamNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
