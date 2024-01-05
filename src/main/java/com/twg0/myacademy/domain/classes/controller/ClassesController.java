package com.twg0.myacademy.domain.classes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twg0.myacademy.domain.academy.DTO.AcademyResponse;
import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.service.ClassesService;
import com.twg0.myacademy.domain.common.exception.ErrorCode;
import com.twg0.myacademy.domain.common.exception.entitynotfound.AcademyNotFoundException;
import com.twg0.myacademy.domain.common.exception.entitynotfound.ClassesNotFoundException;
import com.twg0.myacademy.domain.common.exception.invalidvalue.DuplicatedException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("academy/{academyUserId}/classes")
@RequiredArgsConstructor
public class ClassesController {
	private final AcademyService academyService;
	private final ClassesService classesService;

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

	private boolean isAcademyExist(String academyUserId) {
		return academyService.exist(academyUserId);
	}

	private boolean isClassesExist(String className) {
		return classesService.existByClassName(className);
	}
}
