package com.twg0.myacademy.domain.exam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.exam.DTO.ExamRequest;
import com.twg0.myacademy.domain.exam.DTO.ExamResponse;
import com.twg0.myacademy.domain.exam.entity.Exam;
import com.twg0.myacademy.domain.exam.repository.ExamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ExamService {
	private final ExamRepository examRepository;
	private final ClassesRepository classesRepository;

	@Transactional
	public ExamResponse create(String className, ExamRequest examRequest) {
		if (!classesRepository.existsByClassName(className))
			throw new IllegalArgumentException("반이 존재하지 않습니다.");
		if(examRepository.existsByDateName(examRequest.getDateName()))
			throw new IllegalArgumentException("해당 시험이 이미 존재한다.");
		Classes classes = classesRepository.findByClassName(className).get();
		Exam exam = examRequest.toEntity();
		exam.setClasses(classes);
		examRepository.save(exam);
		return ExamResponse.fromEntity(exam);
	}

	@Transactional
	public ExamResponse updateInfo(ExamRequest examRequest, String dateName) {
		if(!examRepository.existsByDateName(dateName))
			throw new IllegalArgumentException("해당 시험이 존재하지 않습니다.");
		Exam exam = examRepository.findByDateName(dateName).get();
		exam.updateInfo(examRequest);
		return ExamResponse.fromEntity(exam);
	}

	@Transactional
	public void delete(String dateName) {
		if(!examRepository.existsByDateName(dateName))
			throw new IllegalArgumentException("해당 시험이 존재하지 않습니다.");
		Exam exam = examRepository.findByDateName(dateName).get();
		exam.getClasses().removeExams(exam);
		examRepository.deleteByDateName(dateName);
	}

	public ExamResponse read(String dateName) {
		if(!examRepository.existsByDateName(dateName))
			throw new IllegalArgumentException("해당 시험이 존재하지 않습니다.");
		Exam exam = examRepository.findByDateName(dateName).get();
		return ExamResponse.fromEntity(exam);
	}

	public boolean existsByDateName(String dateName) {
		return examRepository.findByDateName(dateName).isPresent();
	}
}
