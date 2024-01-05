package com.twg0.myacademy.domain.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// Academy
	ACADEMY_NOT_FOUND("ACADEMY_001", "해당 학원을 찾을 수 없습니다.", 404),
	// Classes
	CLASSES_NOT_FOUND("CLASSES_001", "해당 반을 찾을 수 없습니다.", 404),
	// Member
	MEMBER_NOT_FOUND("MEMBER_001", "해당 사용자를 찾을 수 없습니다.", 404),
	// MemberClasses

	// Exam
	EXAM_NOT_FOUND("EXAM_001", "해당 시험을 찾을 수 없습니다.", 404),
	// Grade

	// Common
	ID_DUPLICATED("DUPLICATED_001", "이미 존재하는 ID입니다.", 409),

	INTERNAL_SERVER_ERROR("SERVER_001", "서버에서 오류가 발생하였습니다.", 500),
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
