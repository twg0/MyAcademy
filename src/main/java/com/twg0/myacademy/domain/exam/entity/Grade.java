package com.twg0.myacademy.domain.exam.entity;

import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grades_id")
	private Long id;

	@Lob
	private String score;

	protected Grade(String score, Member member, Exam exam) {
		this.score = score;
		this.member = member;
		this.exam = exam;
	}

	public static Grade createGrade(String score, Member member, Exam exam) {
		Grade grade = new Grade(score, member, exam);
		member.addGrades(grade);
		exam.addGrades(grade);
		return grade;
	}

	public void update(String score) {
		this.score = score;
	}

	/* 연관관계 설정 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id")
	private Exam exam;
}
