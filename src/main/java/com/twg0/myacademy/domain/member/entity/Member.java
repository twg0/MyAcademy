package com.twg0.myacademy.domain.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.exam.entity.Grade;
import com.twg0.myacademy.domain.member.DTO.MemberRequest;
import com.twg0.myacademy.domain.member.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, unique = true, length = 20)
	private String userId;

	@Column(nullable = false, length = 20)
	private String username;

	@Column(nullable = false, length = 20)
	private String password;

	@Column(nullable = false, length = 20)
	private LocalDateTime birth;

	@Column(length = 20)
	private String school;

	@Column(nullable = false, length = 20)
	private Integer age;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	public void updateInfo(MemberRequest memberRequest) {
		this.userId = memberRequest.getUserId();
		this.username = memberRequest.getUsername();
		this.password = memberRequest.getPassword();
		this.birth = memberRequest.getBirth();
		this.school = memberRequest.getSchool();
		this.age = memberRequest.getAge();
	}

	@Builder
	public Member(String userId, String username, String password, LocalDateTime birth, String school, Integer age, Role role,
		Academy academy) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.birth = birth;
		this.school = school;
		this.age = age;
		this.role = role;
		this.academy = academy;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/* 연관관계 필요 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "academy_id", nullable = false)
	private Academy academy;

	@OneToMany(mappedBy = "member")
	private List<MemberClasses> memberClasses = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Grade> grades = new ArrayList<>();

	public void setAcademy(Academy academy) {
		this.academy = academy;
		academy.addMembers(this);
	}
	public void addMemberClasses(MemberClasses memberClasses) {
		this.memberClasses.add(memberClasses);
	}

	public void removeMemberClasses(MemberClasses mc) {
		this.memberClasses.remove(mc);
	}

	public void addGrades(Grade grade) {
		this.grades.add(grade);
	}

	public void removeGrades(Grade grade) {
		this.grades.remove(grade);
	}
}
