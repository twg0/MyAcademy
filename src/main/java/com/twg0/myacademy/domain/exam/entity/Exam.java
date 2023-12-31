package com.twg0.myacademy.domain.exam.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.exam.DTO.ExamRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exam_id")
	private Long id;

	@Column(unique = true)
	private String dateName;

	@Column(length = 30)
	private String name;

	private LocalDateTime date;

	@Column(length = 9)
	private Integer countOfStudent;

	public void updateInfo(ExamRequest examRequest) {
		this.name = examRequest.getName();
		this.date = examRequest.getDate();
		this.countOfStudent = examRequest.getCountOfStudent();
		this.dateName = examRequest.getDate().toString() + examRequest.getName();
	}

	@Builder
	public Exam(String name, LocalDateTime date, Integer countOfStudent, Classes classes) {
		this.dateName = date.toString() + name;
		this.name = name;
		this.date = date;
		this.countOfStudent = countOfStudent;
		this.classes = classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
		classes.addExams(this);
	}

	/* 연관관계 설정 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classes_id", nullable = false)
	private Classes classes;

	@OneToMany(mappedBy = "exam")
	private List<Grade> grades = new ArrayList<>();

	public void addGrades(Grade grade) {
		grades.add(grade);
	}

	public void removeGrades(Grade grade) {
		grades.remove(grade);
	}
}
