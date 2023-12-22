package com.twg0.myacademy.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
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

	@Transactional
	public MemberResponse create(MemberRequest memberRequest, String academyUserId) {
		if(memberRepository.existsByUserId(memberRequest.getUserId())) {
			throw new IllegalArgumentException("ID가 이미 존재합니다.");
		}
		Academy academy = academyRepository.findByUserId(academyUserId).get();
		memberRequest.setAcademy(academy);
		Member member = memberRepository.save(memberRequest.toEntity());
		return MemberResponse.fromEntity(member);
	}

	@Transactional
	public MemberResponse updateInfo(MemberRequest memberRequest, String userId) {
		if(memberRepository.existsByUserId(memberRequest.getUserId())) {
			throw new IllegalArgumentException("ID가 이미 존재합니다.");
		}
		Member member = memberRepository.findByUserId(userId).get();
		member.updateInfo(memberRequest);
		return MemberResponse.fromEntity(member);
	}
}
