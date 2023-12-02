package com.twg0.myacademy.domain.academy.entity;

import java.util.ArrayList;
import java.util.List;

import com.twg0.myacademy.domain.common.entity.BaseEntity;
import com.twg0.myacademy.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Academy extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "academy_id")
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
	@OneToMany(mappedBy = "academy")
	private List<Member> members = new ArrayList<>();

	public void addMembers(Member member) {
		this.members.add(member);
	}

	public void removeMembers(Member member) {
		this.members.remove(member);
	}
}
