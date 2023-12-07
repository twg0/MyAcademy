package com.twg0.myacademy.domain.classes.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MemberClassesID implements Serializable {

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "classes_id")
	private Long classesId;
}
