package com.twg0.myacademy.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.common.exception.ErrorCode;
import com.twg0.myacademy.domain.common.exception.entitynotfound.AcademyNotFoundException;
import com.twg0.myacademy.domain.common.exception.entitynotfound.MemberNotFoundException;
import com.twg0.myacademy.domain.common.exception.invalidvalue.DuplicatedException;
import com.twg0.myacademy.domain.member.DTO.MemberRequest;
import com.twg0.myacademy.domain.member.DTO.MemberResponse;
import com.twg0.myacademy.domain.member.enums.Role;
import com.twg0.myacademy.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("academy/{academyUserId}/member")
@RequiredArgsConstructor
public class MemberController {
	private final AcademyService academyService;
	private final MemberService memberService;

	@GetMapping("{memberUserId}")
	public ResponseEntity<MemberResponse> findOne(
		@PathVariable("academyUserId") String academyUserId,
		@PathVariable("memberUserId") String memberUserId
	) {
		if(!isAcademyExist(academyUserId))
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);
		if(!isMemberExist(memberUserId))
			throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);

		MemberResponse response = memberService.read(memberUserId);
		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}

	private boolean isAcademyExist(String academyUserId) {
		return academyService.exist(academyUserId);
	}

	private boolean isMemberExist(String memberUserId) {
		return memberService.exist(memberUserId);
	}

	@PostMapping
	public ResponseEntity<MemberResponse> create(
		@PathVariable("academyUserId") String academyUserId,
		@RequestBody MemberRequest memberRequest
	) {
		if(!isAcademyExist(academyUserId))
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);
		if(isMemberExist(memberRequest.getUserId()))
			throw new DuplicatedException(ErrorCode.ID_DUPLICATED);

		MemberResponse response = memberService.create(memberRequest, academyUserId, Role.MEMBER);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
