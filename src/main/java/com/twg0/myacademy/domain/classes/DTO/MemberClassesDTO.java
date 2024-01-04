package com.twg0.myacademy.domain.classes.DTO;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberClassesDTO {
	private Member member;
	private Classes classes;

	@Builder
	public MemberClassesDTO(Member member, Classes classes) {
		this.member = member;
		this.classes = classes;
	}

	public static MemberClassesDTO fromEntity(MemberClasses memberClasses) {
		return MemberClassesDTO.builder()
			.classes(memberClasses.getClasses())
			.member(memberClasses.getMember())
			.build();
	}

	public MemberClasses toEntity() {
		return MemberClasses.builder()
			.member(this.member)
			.classes(this.classes)
			.build();
	}
}
