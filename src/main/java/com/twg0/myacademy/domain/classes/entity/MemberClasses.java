package com.twg0.myacademy.domain.classes.entity;

import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberClasses extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_classes_id")
	private Long id;

	/* 연관관계 설정 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classes_id")
	private Classes classes;

	@Builder
	public MemberClasses(Member member, Classes classes) {
		this.member = member;
		this.classes = classes;
	}
}
