package com.twg0.myacademy.domain.academy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twg0.myacademy.domain.academy.DTO.AcademyRequest;
import com.twg0.myacademy.domain.academy.DTO.AcademyResponse;
import com.twg0.myacademy.domain.academy.service.AcademyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/academy")
@RequiredArgsConstructor
public class AcademyController {
	private final AcademyService academyService;

	@GetMapping("/{academyUserId}")
	public ResponseEntity<AcademyResponse> findOne(
		@PathVariable("academyUserId") String academyUserId
	) {
		AcademyResponse academyResponse = academyService.read(academyUserId);
		return new ResponseEntity<>(academyResponse, HttpStatus.OK);
	}
}
