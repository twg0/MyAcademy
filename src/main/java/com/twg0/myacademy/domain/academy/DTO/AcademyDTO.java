package com.twg0.myacademy.domain.academy.DTO;

import com.twg0.myacademy.domain.academy.entity.Academy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademyDTO {

	private String name;
	private String phoneNumber;
	private String address;
	private Integer studentNumber;
	private String userId;
	private String password;

	@Builder
	public AcademyDTO(String name, String phoneNumber, String address, Integer studentNumber, String userId,
		String password) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.studentNumber = studentNumber;
		this.userId = userId;
		this.password = password;
	}


	public static AcademyDTO fromEntity(Academy academy) {
		return AcademyDTO.builder()
			.name(academy.getName())
			.phoneNumber(academy.getPhoneNumber())
			.address(academy.getAddress())
			.studentNumber(academy.getStudentNumber())
			.userId(academy.getUserId())
			.password(academy.getPassword())
			.build();
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
