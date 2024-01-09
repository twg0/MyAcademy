package com.twg0.myacademy.domain.classes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClassesService {
	private final ClassesRepository classesRepository;
	private final AcademyRepository academyRepository;
	private final MemberRepository memberRepository;

	/**
	 * 반 생성
	 * @param classesRequest
	 * @param academyUserId
	 * @return
	 */
	@Transactional
	public ClassesResponse create(ClassesRequest classesRequest, String academyUserId) {
		Academy academy = academyRepository.findByAcademyUserId(academyUserId).get();
		Classes classes = classesRequest.toEntity();
		classes.setAcademy(academy);
		classesRepository.save(classes);
		return ClassesResponse.fromEntity(classes);
	}

	/**
	 * 반 수정
	 * @param className
	 * @param classesRequest
	 * @return
	 */
	@Transactional
	public ClassesResponse updateInfo(String className, ClassesRequest classesRequest) {
		Classes classes = classesRepository.findByClassName(className).get();
		classes.updateInfo(classesRequest);
		return ClassesResponse.fromEntity(classes);
	}

	/**
	 * 반 삭제
	 * @param className
	 */
	@Transactional
	public void delete(String className) {
		Classes classes = classesRepository.findByClassName(className).get();
		classes.getAcademy().removeClasses(classes);
		classesRepository.deleteByClassName(className);
	}

	/**
	 * 반 정보 읽기
	 * @param className
	 * @return
	 */
	public ClassesResponse read(String className) {
		Classes classes = classesRepository.findByClassName(className).get();
		return ClassesResponse.fromEntity(classes);
	}

	/**
	 * 전체 반 정보 읽기
	 * @return
	 */
	public List<ClassesResponse> readAll() {
		List<Classes> classesList = classesRepository.findAll();
		return classesList.stream().map(ClassesResponse::fromEntity).toList();
	}

	@Transactional
	public ClassesResponse register(String className, String memberUserId) {
		Classes classes = classesRepository.findByClassName(className).get();
		Member member = memberRepository.findByUserId(memberUserId).get();
		MemberClasses.createMemberClasses(member, classes);
		return ClassesResponse.fromEntity(classes);
	}

	@Transactional
	public ClassesResponse deleteMember(String className, String memberUserId, Long memberClassesId) {
		Classes classes = classesRepository.findByClassName(className).get();
		Member member = memberRepository.findByUserId(memberUserId).get();
		classes.removeMemberClasses(memberClassesId);
		member.removeMemberClasses(memberClassesId);
		return ClassesResponse.fromEntity(classes);
	}

	public boolean existByClassName(String className) {
		return classesRepository.findByClassName(className).isPresent();
	}

	public boolean existByMemberUserId(String memberUserId) {
		return memberRepository.findByUserId(memberUserId).isPresent();
	}
}
