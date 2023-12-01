package com.twg0.myacademy.domain.member.entity;

import java.time.Instant;

import com.twg0.myacademy.domain.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private Instant birth;
	private String school;
	private Integer age;

	@Builder
	public Member(String username, Instant birth, String school, Integer age) {
		this.username = username;
		this.birth = birth;
		this.school = school;
		this.age = age;
	}

	/* 연관관계 필요 */
}
