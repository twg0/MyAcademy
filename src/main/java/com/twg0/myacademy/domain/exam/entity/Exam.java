package com.twg0.myacademy.domain.exam.entity;

import java.time.Instant;

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
public class Exam {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Instant date;
	private Integer countOfStudent;

	@Builder
	public Exam(String name, Instant date, Integer countOfStudent) {
		this.name = name;
		this.date = date;
		this.countOfStudent = countOfStudent;
	}

	/* 연관관계 설정 */
}
