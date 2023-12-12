package com.twg0.myacademy.domain.academy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.DTO.AcademyDTO;
import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AcademyService {

	private final AcademyRepository academyRepository;

	@Transactional
	public AcademyDTO create(AcademyDTO academyDTO) {
		log.info("create 진입");
		Academy save = academyRepository.save(academyDTO.toEntity());
		return AcademyDTO.fromEntity(save);
	}

	public AcademyDTO readByUserId(String userId) {
		Optional<Academy> result = academyRepository.findByUserId(userId);
		return AcademyDTO.fromEntity(result.get());
	}

	public List<AcademyDTO> readAll() {
		List<Academy> all = academyRepository.findAll();
		return all.stream().map(AcademyDTO::fromEntity).toList();
	}
}
