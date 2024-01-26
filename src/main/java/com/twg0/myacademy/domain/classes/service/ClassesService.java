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
import com.twg0.myacademy.domain.classes.repository.MemberClassesRepository;
import com.twg0.myacademy.domain.member.DTO.MemberResponse;
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
	private final MemberClassesRepository memberClassesRepository;


	/*
	반 관련 메소드
	 */

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

	/*
	반-멤버 관련 메소드
	 */

	@Transactional
	public ClassesResponse register(String className, String memberUserId) {
		Classes classes = classesRepository.findByClassName(className).get();
		Member member = memberRepository.findByUserId(memberUserId).get();
		memberClassesRepository.save(MemberClasses.createMemberClasses(member, classes));
		return ClassesResponse.fromEntity(classes);
	}

	@Transactional
	public ClassesResponse deleteMember(String className, String memberUserId) {
		Classes classes = classesRepository.findByClassName(className).get();
		Member member = memberRepository.findByUserId(memberUserId).get();
		MemberClasses memberClasses = memberClassesRepository.findByMemberAndClasses(member, classes).get();
		classes.removeMemberClasses(memberClasses);
		member.removeMemberClasses(memberClasses);
		memberClassesRepository.delete(memberClasses);
		return ClassesResponse.fromEntity(classes);
	}

	/**
	 * 해당 학생이 포함된 모든 반 조회
	 */
	public List<ClassesResponse> findAllByMemerUserId(String memberUserId) {
		Member member = memberRepository.findByUserId(memberUserId).get();
		List<Classes> classes = member.getMemberClasses().stream().map(MemberClasses::getClasses).toList();
		return classes.stream().map(ClassesResponse::fromEntity).toList();
	}

	public boolean existByClassNameAndMember(String className, String memberUserId) {
		Classes classes = classesRepository.findByClassName(className).get();
		Member member = memberRepository.findByUserId(memberUserId).get();
		return memberClassesRepository.findByMemberAndClasses(member, classes)
			.isPresent();
	}

	public boolean existByClassName(String className) {
		return classesRepository.findByClassName(className).isPresent();
	}
}
