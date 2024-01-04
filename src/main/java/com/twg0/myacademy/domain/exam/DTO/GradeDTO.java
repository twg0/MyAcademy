package com.twg0.myacademy.domain.exam.DTO;

import com.twg0.myacademy.domain.exam.entity.Exam;
import com.twg0.myacademy.domain.exam.entity.Grade;
import com.twg0.myacademy.domain.member.entity.Member;

import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GradeDTO {
	private String memberExam;
	private String score;

	@Builder
	public GradeDTO(String memberExam, String score) {
		this.memberExam = memberExam;
		this.score = score;
	}

	public static GradeDTO fromEntity(Grade grade) {
		return GradeDTO.builder()
			.memberExam(grade.getMemberExam())
			.score(grade.getScore())
			.build();
	}
}
