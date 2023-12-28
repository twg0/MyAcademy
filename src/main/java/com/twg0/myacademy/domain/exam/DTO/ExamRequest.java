package com.twg0.myacademy.domain.exam.DTO;

import java.time.LocalDateTime;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.exam.entity.Exam;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
public class ExamRequest {
	private String dateName;
	private String name;
	private LocalDateTime date;
	private Integer countOfStudent;

	@Builder
	public ExamRequest(String dateName, String name, LocalDateTime date, Integer countOfStudent) {
		this.dateName = dateName;
		this.name = name;
		this.date = date;
		this.countOfStudent = countOfStudent;
	}

	public Exam toEntity() {
		return Exam.builder()
			.name(this.name)
			.date(this.date)
			.countOfStudent(this.countOfStudent)
			.build();
	}
}
