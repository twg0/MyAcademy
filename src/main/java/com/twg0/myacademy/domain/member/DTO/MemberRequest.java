package com.twg0.myacademy.domain.member.DTO;

import java.time.Instant;
import java.time.LocalDateTime;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberRequest {
	private String userId;
	private String username;
	private String password;
	private LocalDateTime birth;
	private String school;
	private Integer age;
	private Role role;
	private Academy academy;

	@Builder
	public MemberRequest(String userId, String username, String password, LocalDateTime birth, String school,
		Integer age,
		Role role, Academy academy) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.birth = birth;
		this.school = school;
		this.age = age;
		this.role = role;
		this.academy = academy;
	}

	public Member toEntity() {
		return Member.builder()
			.userId(this.userId)
			.username(this.username)
			.password(this.password)
			.age(this.age)
			.birth(this.birth)
			.school(this.school)
			.academy(this.academy)
			.role(this.role)
			.build();
	}
}
