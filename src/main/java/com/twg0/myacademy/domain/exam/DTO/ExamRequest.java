package com.twg0.myacademy.domain.exam.DTO;

import java.time.LocalDateTime;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.exam.entity.Exam;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamRequest {
	private String dateName;
	private String name;
	private LocalDateTime date;
	private Integer countOfStudent;

	@Builder
	public ExamRequest(String name, LocalDateTime date, Integer countOfStudent) {
		this.dateName = date.toString() + name;
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
