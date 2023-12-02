package com.twg0.myacademy.domain.exam.entity;

import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String score;

	public Grade(String score) {
		this.score = score;
	}

	/* 연관관계 설정 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id")
	private Grade grade;

	@Builder
	public Grade(String score, Member member, Grade grade) {
		this.score = score;
		this.member = member;
		this.grade = grade;
	}
}
