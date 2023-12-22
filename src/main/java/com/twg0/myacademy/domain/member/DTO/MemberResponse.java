package com.twg0.myacademy.domain.member.DTO;

import java.time.LocalDateTime;

import com.twg0.myacademy.domain.member.entity.Member;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberResponse {
	private String userId;
	private String username;
	private LocalDateTime birth;
	private String school;
	private Integer age;

	@Builder
	public MemberResponse(String userId, String username, LocalDateTime birth, String school, Integer age) {
		this.userId = userId;
		this.username = username;
		this.birth = birth;
		this.school = school;
		this.age = age;
	}

	public static MemberResponse fromEntity(Member member) {
		return MemberResponse.builder()
			.userId(member.getUserId())
			.username(member.getUsername())
			.birth(member.getBirth())
			.age(member.getAge())
			.school(member.getSchool())
			.build();
	}
}
