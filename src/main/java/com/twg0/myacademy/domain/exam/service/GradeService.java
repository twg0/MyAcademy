package com.twg0.myacademy.domain.exam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		Member member = optionalMember.get();
		Exam exam = optionalExam.get();
		Grade grade = Grade.builder()
			.exam(exam)
			.member(member)
			.score(score)
			.build();
		Grade save = gradeRepository.save(grade);
		member.addGrades(grade);

		return GradeDTO.fromEntity(save);
	}

	@Transactional
	public void delete(String memberExam) {
		Optional<Grade> optionalGrade = gradeRepository.findByMemberExam(memberExam);
		if(optionalGrade.isEmpty())
			throw new IllegalArgumentException("존재하지 않는 성적입니다.");
		Grade grade = optionalGrade.get();
		grade.getMember().removeGrades(grade);
		grade.getExam().removeGrades(grade);
		gradeRepository.delete(grade);
	}

	@Transactional
	public GradeDTO update(String memberExam, String score) {
		Optional<Grade> optionalGrade = gradeRepository.findByMemberExam(memberExam);
		if(optionalGrade.isEmpty())
			throw new IllegalArgumentException("존재하지 않는 성적입니다.");
		Grade grade = optionalGrade.get();
		grade.update(score);
		return GradeDTO.fromEntity(grade);
	}

	public List<GradeDTO> readAllByMember(String memberUserId) {
		Optional<Member> optionalMember = memberRepository.findByUserId(memberUserId);
		if(optionalMember.isEmpty())
			throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
		List<Grade> gradeList = gradeRepository.findAllByMember(optionalMember.get());
		return gradeList.stream().map(GradeDTO::fromEntity).toList();
	}

	public List<GradeDTO> readAllByExam(String dateName) {
		Optional<Exam> optionalExam = examRepository.findByDateName(dateName);
		if(optionalExam.isEmpty())
			throw new IllegalArgumentException("존재하지 않는 시험입니다.");
		List<Grade> gradeList = gradeRepository.findAllByExam(optionalExam.get());
		return gradeList.stream().map(GradeDTO::fromEntity).toList();
	}
}
