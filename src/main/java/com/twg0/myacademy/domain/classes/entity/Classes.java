package com.twg0.myacademy.domain.classes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.exam.entity.Exam;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Classes extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "classes_id")
	private Long id;

	@Column(unique = true, nullable = false)
	private String className;

	@Column(nullable = false)
	private String subject;

	@Column(nullable = false)
	private String teacher;

	@Column(nullable = false)
	private Integer countOfStudent;

	@Builder
	public Classes(String subject, String className, String teacher, Integer countOfStudent, Academy academy) {
		this.subject = subject;
		this.className = className;
		this.teacher = teacher;
		this.countOfStudent = countOfStudent;
		this.academy = academy;
	}

	public static Classes createClasses(String subject, String className, String teacher, Integer countOfStudent,
		Academy academy) {
		Classes classes = Classes.builder()
			.subject(subject)
			.className(className)
			.teacher(teacher)
			.countOfStudent(countOfStudent)
			.academy(academy)
			.build();

		academy.addClasses(classes);
		return classes;
	}

	public void updateInfo(ClassesRequest classesRequest) {
		this.className = classesRequest.getClassName();
		this.subject = classesRequest.getSubject();
		this.teacher = classesRequest.getTeacher();
		this.countOfStudent = classesRequest.getCountOfStudent();
	}

	/* 연관관계 설정 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "academy_id")
	private Academy academy;

	@OneToMany(mappedBy = "classes")
	private List<Exam> exams = new ArrayList<>();

	@OneToMany(mappedBy = "classes")
	private List<MemberClasses> memberClasses = new ArrayList<>();

	public void setAcademy(Academy academy) {
		this.academy = academy;
		academy.addClasses(this);
	}

	public void addMemberClasses(MemberClasses memberClasses) {
		this.memberClasses.add(memberClasses);
	}

	public void removeMemberClasses(MemberClasses memberClasses) {
		this.memberClasses.remove(memberClasses);
	}

	public void addExams(Exam exam) {
		this.exams.add(exam);
	}

	public void removeExams(Exam exam) {
		this.exams.remove(exam);
	}

}
