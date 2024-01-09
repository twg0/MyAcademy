package com.twg0.myacademy.domain.academy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.DTO.AcademyRequest;
import com.twg0.myacademy.domain.academy.DTO.AcademyResponse;
import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AcademyService {

	private final AcademyRepository academyRepository;

	@Transactional
	public AcademyResponse create(AcademyRequest academyRequest) {
		Academy save = academyRepository.save(academyRequest.toEntity());
		return AcademyResponse.fromEntity(save);
	}

	@Transactional
	public AcademyResponse updateInfo(String academyUserId, AcademyRequest academyRequest) {
		Optional<Academy> result = academyRepository.findByAcademyUserId(academyUserId);
		return result.get().updateInfo(academyRequest);
	}

	@Transactional
	public void delete(String academyUserId) {
		academyRepository.deleteByAcademyUserId(academyUserId);
	}

	public AcademyResponse read(String academyUserId) {
		Optional<Academy> result = academyRepository.findByAcademyUserId(academyUserId);
		return AcademyResponse.fromEntity(result.get());
	}

	public List<AcademyResponse> readAll() {
		List<Academy> all = academyRepository.findAll();
		return all.stream().map(AcademyResponse::fromEntity).toList();
	}

	public boolean exist(String academyUserId) {
		return academyRepository.findByAcademyUserId(academyUserId).isPresent();
	}
}
