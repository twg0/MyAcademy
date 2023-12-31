package com.twg0.myacademy.domain.academy.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.twg0.myacademy.domain.academy.DTO.AcademyRequest;
import com.twg0.myacademy.domain.academy.DTO.AcademyResponse;
import com.twg0.myacademy.domain.classes.entity.Classes;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Academy extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "academy_id")
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	@ColumnDefault("0")
	private Integer studentNumber;

	@Column(unique = true, nullable = false, length = 30)
	private String academyUserId;

	@Column(nullable = false)
	private String password;

	@Builder
	public Academy(String name, String phoneNumber, String address, Integer studentNumber, String academyUserId,
		String password) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.studentNumber = studentNumber;
		this.academyUserId = academyUserId;
		this.password = password;
	}

	/* 메소드 */
	public AcademyResponse updateInfo(AcademyRequest academyRequest) {
		this.name = academyRequest.getName();
		this.phoneNumber = academyRequest.getPhoneNumber();
		this.address = academyRequest.getAddress();
		this.studentNumber = academyRequest.getStudentNumber();
		return AcademyResponse.fromEntity(this);
	}

	/* 연관관계 필요 */
	@OneToMany(mappedBy = "academy")
	private List<Member> members = new ArrayList<>();

	@OneToMany(mappedBy = "academy")
	private List<Classes> classes = new ArrayList<>();

	public void addMembers(Member member) {
		this.members.add(member);
	}

	public void removeMembers(Member member) {
		this.members.remove(member);
	}

	public void addClasses(Classes classes) {
		this.classes.add(classes);
	}

	public void removeClasses(Classes classes) {
		this.classes.remove(classes);
	}
}
