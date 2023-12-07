package com.twg0.myacademy.domain.classes.entity;

import java.util.ArrayList;
import java.util.List;

import com.twg0.myacademy.domain.academy.entity.Academy;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Classes extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "classes_id")
	private Long id;

	private String subject;
	private String className;
	private Integer countOfStudent;

	@Builder
	public Classes(String subject, String className, Integer countOfStudent) {
		this.subject = subject;
		this.className = className;
		this.countOfStudent = countOfStudent;
	}

	/* 연관관계 설정 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "academy_id")
	private Academy academy;

	@OneToMany(mappedBy = "classes")
	private List<Exam> exams = new ArrayList<>();

	@OneToMany(mappedBy = "classes")
	private List<MemberClasses> memberClasses = new ArrayList<>();

	public void addMemberClasses(MemberClasses memberClasses) {
		this.memberClasses.add(memberClasses);
	}

	public void removeMemberClasses(MemberClasses memberClasses) {
		this.memberClasses.remove(memberClasses);
	}
}
