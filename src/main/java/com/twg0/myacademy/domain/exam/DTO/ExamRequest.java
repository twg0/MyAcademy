package com.twg0.myacademy.domain.exam.DTO;

import java.time.LocalDateTime;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.exam.entity.Exam;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
public class ExamRequest {
	private String name;
	private LocalDateTime date;
	private Integer countOfStudent;
	private Classes classes;

	@Builder
	public ExamRequest(String name, LocalDateTime date, Integer countOfStudent, Classes classes) {
		this.name = name;
		this.date = date;
		this.countOfStudent = countOfStudent;
		this.classes = classes;
	}

	public Exam toEntity() {
		return Exam.builder()
			.name(this.name)
			.date(this.date)
			.countOfStudent(this.countOfStudent)
			.classes(this.classes)
			.build();
	}
}
