package com.twg0.myacademy.domain.academy.controller;

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

import com.twg0.myacademy.domain.academy.DTO.AcademyRequest;
import com.twg0.myacademy.domain.academy.DTO.AcademyResponse;
import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.common.exception.ErrorCode;
import com.twg0.myacademy.domain.common.exception.entitynotfound.AcademyNotFoundException;
import com.twg0.myacademy.domain.common.exception.invalidvalue.DuplicatedException;

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
		if(!isAcademyExist(academyUserId))
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);

		AcademyResponse academyResponse = academyService.read(academyUserId);
		return new ResponseEntity<>(academyResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<AcademyResponse> create(
		@RequestBody AcademyRequest academyRequest
	) {
		if(isAcademyExist(academyRequest.getAcademyUserId()))
			throw new DuplicatedException(ErrorCode.ID_DUPLICATED);

		AcademyResponse academyResponse = academyService.create(academyRequest);
		return new ResponseEntity<>(academyResponse, HttpStatus.CREATED);
	}

	@PatchMapping("/{academyUserId}")
	public ResponseEntity<AcademyResponse> update(
		@PathVariable("academyUserId") String academyUserId,
		@RequestBody AcademyRequest academyRequest
	) {
		if(!isAcademyExist(academyUserId))
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);

		AcademyResponse academyResponse = academyService.updateInfo(academyUserId, academyRequest);
		return new ResponseEntity<>(academyResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{academyUserId}")
	public ResponseEntity<Void> delete(
		@PathVariable("academyUserId") String academyUserId
	) {
		if(!isAcademyExist(academyUserId))
			throw new AcademyNotFoundException(ErrorCode.ACADEMY_NOT_FOUND);

		academyService.delete(academyUserId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private boolean isAcademyExist(String academyUserId) {
		return academyService.exist(academyUserId);
	}
}
