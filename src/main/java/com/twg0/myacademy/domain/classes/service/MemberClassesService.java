package com.twg0.myacademy.domain.classes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.classes.DTO.MemberClassesDTO;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.classes.repository.MemberClassesRepository;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberClassesService {
	private final MemberClassesRepository memberClassesRepository;
	private final MemberRepository memberRepository;
	private final ClassesRepository classesRepository;

	@Transactional
	public MemberClassesDTO create(String memberUserId, String className) {
		Member member = memberRepository.findByUserId(memberUserId).get();
		Classes classes = classesRepository.findByClassName(className).get();
		if(memberClassesRepository.existsByMemberAndClasses(member, classes))
			throw new IllegalArgumentException("이미 해당 반을 수강중입니다.");
		MemberClassesDTO dto = MemberClassesDTO.builder()
			.member(member)
			.classes(classes)
			.build();
		MemberClasses save = memberClassesRepository.save(dto.toEntity());
		member.addMemberClasses(save);
		classes.addMemberClasses(save);
		return dto;
	}

	@Transactional
	public void delete(String memberUserId, String className) {
		Member member = memberRepository.findByUserId(memberUserId).get();
		Classes classes = classesRepository.findByClassName(className).get();
		if(!memberClassesRepository.existsByMemberAndClasses(member, classes))
			throw new IllegalArgumentException("해당 반을 수강 중이지 않습니다.");
		memberClassesRepository.deleteByMemberAndClasses(member,classes);
	}
}
