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

	@Builder
	public ExamResponse(String name, LocalDateTime date, Integer countOfStudent) {
		this.name = name;
		this.date = date;
		this.countOfStudent = countOfStudent;
	}

	public static ExamResponse fromEntity(Exam exam) {
		return ExamResponse.builder()
			.name(exam.getName())
			.date(exam.getDate())
			.countOfStudent(exam.getCountOfStudent())
			.build();
	}
}
