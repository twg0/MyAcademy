package com.twg0.myacademy.domain.member.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.exam.entity.Grade;

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
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, length = 20)
	private String username;

	@Column(nullable = false, unique = true, length = 20)
	private String userId;

	@Column(nullable = false, length = 20)
	private String password;

	@Column(nullable = false, length = 20)
	private Instant birth;

	@Column(length = 20)
	private String school;

	@Column(nullable = false, length = 20)
	private Integer age;

	@Builder
	public Member(String username, String userId, String password, Instant birth, String school, Integer age) {
		this.username = username;
		this.userId = userId;
		this.password = password;
		this.birth = birth;
		this.school = school;
		this.age = age;
	}

	/* 연관관계 필요 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "academy_id")
	private Academy academy;

	@OneToMany(mappedBy = "member")
	private List<MemberClasses> memberClasses = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Grade> grades = new ArrayList<>();

	public void setAcademy(Academy academy) {
		this.academy = academy;
	}

	public void addMemberClasses(MemberClasses memberClasses) {
		this.memberClasses.add(memberClasses);
	}

	public void removeMemberClasses(MemberClasses memberClasses) {
		this.memberClasses.remove(memberClasses);
	}

	public void addGrades(Grade grade) {
		this.grades.add(grade);
	}

	public void removeGrades(Grade grade) {
		this.grades.remove(grade);
	}
}
