package com.twg0.myacademy.domain.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// Academy

	// Classes

	// Member

	// MemberClasses

	// Exam

	// Grade



	;
	private final String code;
	private final String message;
	private final int status;

	ErrorCode(String code, String message, int status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
}
