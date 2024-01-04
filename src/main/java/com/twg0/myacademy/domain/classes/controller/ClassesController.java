package com.twg0.myacademy.domain.classes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twg0.myacademy.domain.academy.DTO.AcademyResponse;
import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.service.ClassesService;

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
		if (!academyService.exist(academyUserId)) {
			throw new IllegalArgumentException("존재하지 않는 학원입니다.");
		}
		if (!classesService.existByClassName(className)) {
			throw new IllegalArgumentException("존재하지 않는 반입니다.");
		}
		ClassesResponse classesResponse = classesService.read(className);
		return new ResponseEntity<>(classesResponse, HttpStatus.FOUND);
	}
}
