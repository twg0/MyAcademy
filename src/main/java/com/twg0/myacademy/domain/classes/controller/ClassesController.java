package com.twg0.myacademy.domain.classes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.classes.service.ClassesService;
import com.twg0.myacademy.domain.common.exception.ErrorCode;
import com.twg0.myacademy.domain.common.exception.entitynotfound.AcademyNotFoundException;
import com.twg0.myacademy.domain.common.exception.entitynotfound.ClassesNotFoundException;
import com.twg0.myacademy.domain.common.exception.entitynotfound.MemberClassesNotFoundException;
import com.twg0.myacademy.domain.common.exception.entitynotfound.MemberNotFoundException;
import com.twg0.myacademy.domain.common.exception.invalidvalue.DuplicatedException;
import com.twg0.myacademy.domain.member.DTO.MemberResponse;
import com.twg0.myacademy.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("academy/{academyUserId}/classes")
@RequiredArgsConstructor
public class ClassesController {
	private final AcademyService academyService;
	private final ClassesService classesService;
	private final MemberService memberService;

	@GetMapping("{className}")
	public ResponseEntity<ClassesResponse> findOne(
		@PathVariable("academyUserId") String academyUserId,
		@PathVariable("className") String className
	) {
		if (!isAcademyExist(academyUserId)) {
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);
		}
		if (!isClassesExist(className)) {
			throw new ClassesNotFoundException(ErrorCode.CLASSES_NOT_FOUND);
		}

		ClassesResponse classesResponse = classesService.read(className);
		return new ResponseEntity<>(classesResponse, HttpStatus.FOUND);
	}

	@PostMapping
	public ResponseEntity<ClassesResponse> create(
		@PathVariable("academyUserId") String academyUserId,
		@RequestBody ClassesRequest classesRequest
	) {
		if (!isAcademyExist(academyUserId)) {
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);
		}
		if (isClassesExist(classesRequest.getClassName())) {
			throw new DuplicatedException(ErrorCode.ID_DUPLICATED);
		}

		ClassesResponse classesResponse = classesService.create(classesRequest, academyUserId);
		return new ResponseEntity<>(classesResponse, HttpStatus.CREATED);
	}

	@PatchMapping("{className}")
	public ResponseEntity<ClassesResponse> update(
		@PathVariable("academyUserId") String academyUserId,
		@PathVariable("className") String className,
		@RequestBody ClassesRequest classesRequest
	) {
		if (!isAcademyExist(academyUserId)) {
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);
		}
		if (!isClassesExist(className)) {
			throw new ClassesNotFoundException(ErrorCode.CLASSES_NOT_FOUND);
		}
		if (isClassesExist(classesRequest.getClassName())) {
			throw new DuplicatedException(ErrorCode.ID_DUPLICATED);
		}

		ClassesResponse classesResponse = classesService.updateInfo(className, classesRequest);
		return new ResponseEntity<>(classesResponse, HttpStatus.OK);
	}

	@DeleteMapping("{className}")
	public ResponseEntity<Void> delete (
		@PathVariable("academyUserId") String academyUserId,
		@PathVariable("className") String className
	) {
		if (!isAcademyExist(academyUserId)) {
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);
		}
		if (!isClassesExist(className)) {
			throw new ClassesNotFoundException(ErrorCode.CLASSES_NOT_FOUND);
		}

		classesService.delete(className);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/*
	반-학생 관련 핸들러
	 */
	@PostMapping("{className}/member/{memberUserId}")
	public ResponseEntity<ClassesResponse> register(
		@PathVariable("academyUserId") String academyUserId,
		@PathVariable("className") String className,
		@PathVariable("memberUserId") String memberUserId
	) {
		if (!isAcademyExist(academyUserId)) {
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);
		}
		if (!isClassesExist(className)) {
			throw new ClassesNotFoundException(ErrorCode.CLASSES_NOT_FOUND);
		}
		if (!isMemberExist(memberUserId)) {
			throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
		}
		if(isMemberClassesExist(className, memberUserId)) {
			throw new DuplicatedException(ErrorCode.MEMBER_CLASSES_DUPLICATED);
		}
		ClassesResponse response = classesService.register(className, memberUserId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("{className}/member/{memberUserId}")
	public ResponseEntity<Void> unregister(
		@PathVariable("academyUserId") String academyUserId,
		@PathVariable("className") String className,
		@PathVariable("memberUserId") String memberUserId
	) {
		if (!isAcademyExist(academyUserId)) {
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);
		}
		if (!isClassesExist(className)) {
			throw new ClassesNotFoundException(ErrorCode.CLASSES_NOT_FOUND);
		}
		if (!isMemberExist(memberUserId)) {
			throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
		}
		if(!isMemberClassesExist(className, memberUserId)) {
			throw new MemberClassesNotFoundException(ErrorCode.MEMBER_CLASSES_NOT_FOUND);
		}
		classesService.unregister(className, memberUserId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private boolean isAcademyExist(String academyUserId) {
		return academyService.exist(academyUserId);
	}

	private boolean isClassesExist(String className) {
		return classesService.existByClassName(className);
	}

	private boolean isMemberClassesExist(String className, String memberUserId) {
		return classesService.existByClassNameAndMember(className, memberUserId);
	}

	private boolean isMemberExist(String memberUserId) {
		return memberService.exist(memberUserId);
	}
}
