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
		if(academyRepository.existsByUserId(academyRequest.getUserId()))
			throw new IllegalArgumentException("ID가 이미 존재합니다.");

		Academy save = academyRepository.save(academyRequest.toEntity());
		return AcademyResponse.fromEntity(save);
	}

	@Transactional
	public AcademyResponse updateInfo(String userId, AcademyRequest academyRequest) {
		Optional<Academy> result = academyRepository.findByUserId(userId);
		return result.get().updateInfo(academyRequest);
	}

	@Transactional
	public void delete(String userId) throws IllegalAccessException {
		if(!academyRepository.existsByUserId(userId))
			throw new IllegalAccessException("ID가 존재하지 않습니다.");
		academyRepository.deleteByUserId(userId);
	}

	public AcademyResponse read(String userId) {
		Optional<Academy> result = academyRepository.findByUserId(userId);
		return AcademyResponse.fromEntity(result.get());
	}

	public List<AcademyResponse> readAll() {
		List<Academy> all = academyRepository.findAll();
		return all.stream().map(AcademyResponse::fromEntity).toList();
	}
}
