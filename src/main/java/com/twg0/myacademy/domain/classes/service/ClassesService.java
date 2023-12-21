package com.twg0.myacademy.domain.classes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClassesService {
	private final ClassesRepository classesRepository;
	private final AcademyRepository academyRepository;

	@Transactional
	public ClassesResponse create(ClassesRequest classesRequest, String academyUserId) {
		Academy academy = academyRepository.findByUserId(academyUserId).get();
		classesRequest.setAcademy(academy);
		Classes classes = classesRequest.toEntity();
		classesRepository.save(classes);
		return ClassesResponse.fromEntity(classes);
	}

	@Transactional
	public ClassesResponse updateInfo(String className, ClassesRequest classesRequest) {
		if(classesRepository.existsByClassName(className))
			throw new IllegalArgumentException("반 이름이 이미 존재합니다.");
		Classes classes = classesRepository.findByClassName(className).get();
		classes.updateInfo(classesRequest);
		return ClassesResponse.fromEntity(classes);
	}

	@Transactional
	public void delete(String className) {
		if(!classesRepository.existsByClassName(className))
			throw new IllegalArgumentException("반이 존재하지 않습니다.");
		classesRepository.deleteByClassName(className);
	}

	public ClassesResponse read(String className) {
		if(!classesRepository.existsByClassName(className))
			throw new IllegalArgumentException("반이 존재하지 않습니다.");
		Classes classes = classesRepository.findByClassName(className).get();
		return ClassesResponse.fromEntity(classes);
	}

	public List<ClassesResponse> readAll(String className) {
		if(!classesRepository.existsByClassName(className))
			throw new IllegalArgumentException("반이 존재하지 않습니다.");
		List<Classes> classesList = classesRepository.findAllByClassName(className);
		return classesList.stream().map(ClassesResponse::fromEntity).toList();
	}
}
