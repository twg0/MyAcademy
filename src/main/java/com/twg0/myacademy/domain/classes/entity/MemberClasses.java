package com.twg0.myacademy.domain.classes.entity;

import java.util.Objects;

import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.member.entity.Member;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberClasses extends BaseEntity {

	@EmbeddedId
	private MemberClassesID memberClassesID = new MemberClassesID();

	/* 연관관계 설정 */
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("memberId")
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("classesId")
	@JoinColumn(name = "classes_id")
	private Classes classes;

	@Builder
	public MemberClasses(Member member, Classes classes) {
		this.member = member;
		member.addMemberClasses(this);
		this.classes = classes;
		classes.addMemberClasses(this);
	}

	/* Override */

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MemberClasses that = (MemberClasses)o;
		return member.equals(that.member) && classes.equals(that.classes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(member, classes);
	}
}
