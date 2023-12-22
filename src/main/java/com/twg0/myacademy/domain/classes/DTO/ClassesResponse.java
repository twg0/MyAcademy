package com.twg0.myacademy.domain.classes.DTO;

import com.twg0.myacademy.domain.classes.entity.Classes;

import lombok.Builder;
import lombok.Data;

@Data
public class ClassesResponse {
	private String className;
	private String subject;
	private String teacher;
	private Integer countOfStudent;

	@Builder
	public ClassesResponse(String className, String subject, String teacher, Integer countOfStudent) {
		this.className = className;
		this.subject = subject;
		this.teacher = teacher;
		this.countOfStudent = countOfStudent;
	}

	public static ClassesResponse fromEntity(Classes classes) {
		return ClassesResponse.builder()
			.className(classes.getClassName())
			.subject(classes.getSubject())
			.teacher(classes.getTeacher())
			.countOfStudent(classes.getCountOfStudent())
			.build();
	}
}
