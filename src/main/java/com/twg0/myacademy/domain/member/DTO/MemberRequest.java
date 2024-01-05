package com.twg0.myacademy.domain.member.DTO;

import java.time.LocalDateTime;

import com.twg0.myacademy.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {
	private String userId;
	private String username;
	private String password;
	private LocalDateTime birth;
	private String school;
	private Integer age;

	@Builder
	public MemberRequest(String userId, String username, String password, LocalDateTime birth, String school,
		Integer age) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.birth = birth;
		this.school = school;
		this.age = age;
	}

	public Member toEntity() {
		return Member.builder()
			.userId(this.userId)
			.username(this.username)
			.password(this.password)
			.age(this.age)
			.birth(this.birth)
			.school(this.school)
			.build();
	}
}
