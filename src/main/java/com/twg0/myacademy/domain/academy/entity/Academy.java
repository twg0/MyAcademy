package com.twg0.myacademy.domain.academy.entity;

import com.twg0.myacademy.domain.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Academy extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String phoneNumber;
	private String address;
	private Integer studentNumber;

	@Builder
	public Academy(String name, String phoneNumber, String address, Integer studentNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.studentNumber = studentNumber;
	}

	/* 연관관계 필요 */
}
