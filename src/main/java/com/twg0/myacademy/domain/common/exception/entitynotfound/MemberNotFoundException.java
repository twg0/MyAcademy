package com.twg0.myacademy.domain.common.exception.entitynotfound;

import com.twg0.myacademy.domain.common.exception.ErrorCode;

public class MemberNotFoundException extends EntityNotFoundException{
	public MemberNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public MemberNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
