package com.twg0.myacademy.domain.grade.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String score;

	public Grade(String score) {
		this.score = score;
	}

	/* 연관관계 설정 */
}
