package com.twg0.myacademy.domain.exam.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.exam.DTO.GradeDTO;
import com.twg0.myacademy.domain.exam.entity.Exam;
import com.twg0.myacademy.domain.exam.entity.Grade;
import com.twg0.myacademy.domain.exam.repository.ExamRepository;
import com.twg0.myacademy.domain.exam.repository.GradeRepository;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GradeService {
	private final GradeRepository gradeRepository;
	private final MemberRepository memberRepository;
	private final ExamRepository examRepository;

	@Transactional
	public GradeDTO create(String memberUserId, String dateName, String score) {
		Optional<Member> optionalMember = memberRepository.findByUserId(memberUserId);
		Optional<Exam> optionalExam = examRepository.findByDateName(dateName);
		if(optionalMember.isEmpty())
			throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
		if(optionalExam.isEmpty())
			throw new IllegalArgumentException("존재하지 않는 시험입니다.");
		Grade grade = Grade.builder()
			.exam(optionalExam.get())
			.member(optionalMember.get())
			.score(score)
			.build();
		Grade save = gradeRepository.save(grade);
		return GradeDTO.fromEntity(save);
	}
}
