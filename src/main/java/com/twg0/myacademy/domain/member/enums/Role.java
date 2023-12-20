package com.twg0.myacademy.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	ADMIN("ROLE_ADMIN", "종합 관리자"),
	MANAGER("ROLE_MANAGER", "학원 원장"),
	MEMBER("ROLE_MEMBER", "일반 사용자");

	private final String key;
	private final String title;

}
