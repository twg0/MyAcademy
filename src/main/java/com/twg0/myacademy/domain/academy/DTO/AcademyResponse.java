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
	private String userId;

	@Builder
	public AcademyResponse(String name, String phoneNumber, String address, Integer studentNumber, String userId) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.studentNumber = studentNumber;
		this.userId = userId;
	}


	public static AcademyResponse fromEntity(Academy academy) {
		return AcademyResponse.builder()
			.name(academy.getName())
			.phoneNumber(academy.getPhoneNumber())
			.address(academy.getAddress())
			.studentNumber(academy.getStudentNumber())
			.userId(academy.getUserId())
			.build();
	}
}
