package com.twg0.myacademy.domain.academy.DTO;

import com.twg0.myacademy.domain.academy.entity.Academy;

import lombok.Builder;
import lombok.Data;

@Data
public class AcademyRequest {

	private String name;
	private String phoneNumber;
	private String address;
	private Integer studentNumber;
	private String userId;
	private String password;

	@Builder
	public AcademyRequest(String name, String phoneNumber, String address, Integer studentNumber, String userId,
		String password) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.studentNumber = studentNumber;
		this.userId = userId;
		this.password = password;
	}

	public Academy toEntity() {
		return Academy.builder()
			.name(this.getName())
			.phoneNumber(this.getPhoneNumber())
			.address(this.getAddress())
			.studentNumber(this.getStudentNumber())
			.userId(this.getUserId())
			.password(this.getPassword())
			.build();
	}
}
