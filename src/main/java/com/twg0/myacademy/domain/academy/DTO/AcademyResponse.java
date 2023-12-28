package com.twg0.myacademy.domain.academy.DTO;

import com.twg0.myacademy.domain.academy.entity.Academy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademyResponse {

	private String name;
	private String phoneNumber;
	private String address;
	private Integer studentNumber;
	private String academyUserId;

	@Builder
	public AcademyResponse(String name, String phoneNumber, String address, Integer studentNumber, String academyUserId) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.studentNumber = studentNumber;
		this.academyUserId = academyUserId;
	}


	public static AcademyResponse fromEntity(Academy academy) {
		return AcademyResponse.builder()
			.name(academy.getName())
			.phoneNumber(academy.getPhoneNumber())
			.address(academy.getAddress())
			.studentNumber(academy.getStudentNumber())
			.academyUserId(academy.getAcademyUserId())
			.build();
	}
}
