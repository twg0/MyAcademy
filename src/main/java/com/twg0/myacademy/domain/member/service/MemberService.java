package com.twg0.myacademy.domain.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.member.DTO.MemberRequest;
import com.twg0.myacademy.domain.member.DTO.MemberResponse;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.enums.Role;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final AcademyRepository academyRepository;
	private final ClassesRepository classesRepository;

	@Transactional
	public MemberResponse create(MemberRequest memberRequest, String academyUserId, Role role) {

		Academy academy = academyRepository.findByAcademyUserId(academyUserId).get();
		Member member = memberRequest.toEntity();
		member.setAcademy(academy);
		member.setRole(role);
		memberRepository.save(member);
		return MemberResponse.fromEntity(member);
	}

	@Transactional
	public MemberResponse updateInfo(MemberRequest memberRequest, String userId) {

		Member member = memberRepository.findByUserId(userId).get();
		member.updateInfo(memberRequest);
		return MemberResponse.fromEntity(member);
	}

	@Transactional
	public void delete(String userId) {

		Member member = memberRepository.findByUserId(userId).get();
		member.getAcademy().removeMembers(member);
		memberRepository.deleteByUserId(userId);
	}

	public MemberResponse read(String userId) {

		Member member = memberRepository.findByUserId(userId).get();
		return MemberResponse.fromEntity(member);
	}

	public List<MemberResponse> findByClassName(String className) {
		Classes classes = classesRepository.findByClassName(className).get();
		List<Member> members = classes.getMemberClasses().stream().map(o -> o.getMember()).toList();
		return members.stream().map(MemberResponse::fromEntity).toList();
	}

	public boolean exist(String userId) {
		return memberRepository.existsByUserId(userId);
	}
}
