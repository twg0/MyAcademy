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
		// 중복 검사
		if(academyRepository.existsByAcademyId(academyRequest.getAcademyId()))
			throw new IllegalArgumentException("ID가 이미 존재합니다.");

		Academy save = academyRepository.save(academyRequest.toEntity());
		return AcademyResponse.fromEntity(save);
	}

	@Transactional
	public AcademyResponse updateInfo(String academyId, AcademyRequest academyRequest) {
		Optional<Academy> result = academyRepository.findByAcademyId(academyId);
		return result.get().updateInfo(academyRequest);
	}

	@Transactional
	public void delete(String academyId) throws IllegalAccessException {
		if(!academyRepository.existsByAcademyId(academyId))
			throw new IllegalAccessException("ID가 존재하지 않습니다.");
		academyRepository.deleteByAcademyId(academyId);
	}

	public AcademyResponse read(String academyId) {
		Optional<Academy> result = academyRepository.findByAcademyId(academyId);
		return AcademyResponse.fromEntity(result.get());
	}

	public List<AcademyResponse> readAll() {
		List<Academy> all = academyRepository.findAll();
		return all.stream().map(AcademyResponse::fromEntity).toList();
	}
}
