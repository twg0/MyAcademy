package com.twg0.myacademy.domain.classes.entity;

import com.twg0.myacademy.domain.common.entity.BaseEntity;

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
public class Classes extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String subject;
	private String className;
	private Integer countOfStudent;

	@Builder
	public Classes(String subject, String className, Integer countOfStudent) {
		this.subject = subject;
		this.className = className;
		this.countOfStudent = countOfStudent;
	}

	/* 연관관계 설정 */
}
