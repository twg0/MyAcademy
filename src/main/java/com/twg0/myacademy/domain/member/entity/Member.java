package com.twg0.myacademy.domain.member.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.exam.entity.Grade;
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

	@Column(nullable = false, unique = true, length = 20)
	private String userId;

	@Column(nullable = false, length = 20)
	private String username;

	@Column(nullable = false, length = 20)
	private String password;

	@Column(nullable = false, length = 20)
	private Instant birth;

	@Column(length = 20)
	private String school;

	@Column(nullable = false, length = 20)
	private Integer age;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Builder
	public Member(String userId, String username, String password, Instant birth, String school, Integer age, Role role,
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

	/* 연관관계 필요 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "academy_id", nullable = false)
	private Academy academy;

	@OneToMany(mappedBy = "member")
	private List<MemberClasses> memberClasses = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Grade> grades = new ArrayList<>();

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

	/* Override */

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Member member = (Member)o;
		return id.equals(member.id) && userId.equals(member.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}
}
