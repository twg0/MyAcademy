package com.twg0.myacademy.domain.exam.DTO;

import java.time.LocalDateTime;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.exam.entity.Exam;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
public class ExamResponse {
	private String name;
	private LocalDateTime date;
	private Integer countOfStudent;
	private Classes classes;

	@Builder
	public ExamResponse(String name, LocalDateTime date, Integer countOfStudent, Classes classes) {
		this.name = name;
		this.date = date;
		this.countOfStudent = countOfStudent;
		this.classes = classes;
	}

	public static ExamResponse fromEntity(Exam exam) {
		return ExamResponse.builder()
			.name(exam.getName())
			.date(exam.getDate())
			.countOfStudent(exam.getCountOfStudent())
			.classes(exam.getClasses())
			.build();
	}
}
