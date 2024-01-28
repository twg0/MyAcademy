package com.twg0.myacademy.domain.common.exception.entitynotfound;

import com.twg0.myacademy.domain.common.exception.ErrorCode;

public class MemberClassesNotFoundException extends EntityNotFoundException{

	public MemberClassesNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public MemberClassesNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
