package com.twg0.myacademy.domain.classes.DTO;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.classes.entity.Classes;

import lombok.Builder;
import lombok.Data;

@Data
public class ClassesRequest {
	private String className;
	private String subject;
	private String teacher;
	private Integer countOfStudent;

	@Builder
	public ClassesRequest(String className, String subject, String teacher, Integer countOfStudent) {
		this.className = className;
		this.subject = subject;
		this.teacher = teacher;
		this.countOfStudent = countOfStudent;
	}

	public Classes toEntity() {
		return Classes.builder()
			.className(this.className)
			.subject(this.subject)
			.teacher(this.teacher)
			.countOfStudent(this.countOfStudent)
			.build();
	}
}
