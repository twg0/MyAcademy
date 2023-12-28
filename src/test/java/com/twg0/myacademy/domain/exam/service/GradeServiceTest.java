package com.twg0.myacademy.domain.exam.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.exam.DTO.GradeDTO;
import com.twg0.myacademy.domain.exam.entity.Exam;
import com.twg0.myacademy.domain.exam.repository.ExamRepository;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.enums.Role;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

@SpringBootTest
@Transactional
class GradeServiceTest {
	@Autowired
	private GradeService gradeService;
	@Autowired
	private AcademyRepository academyRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ClassesRepository classesRepository;
	@Autowired
	private ExamRepository examRepository;

	private Academy ACADEMY;
	private Member MEMBER;
	private LocalDateTime BIRTH;
	private Classes CLASSES;
	private Exam EXAM;
	private LocalDateTime DATE;
	@BeforeEach
	public void setUp() {
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyId("seokang")
			.password("tjrkd")
			.build();
		ACADEMY = academyRepository.save(academy);

		BIRTH = LocalDateTime.of(1996, 8, 25, 0, 0).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
		DATE = LocalDateTime.of(2023, 11, 11, 15, 30).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

		final Member member = Member.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.role(Role.MEMBER)
			.academy(ACADEMY)
			.build();

		final Classes classes = Classes.builder()
			.subject("수학")
			.className("예비고1A")
			.countOfStudent(0)
			.teacher("kim")
			.academy(ACADEMY)
			.build();

		MEMBER = memberRepository.save(member);
		CLASSES = classesRepository.save(classes);

		final Exam exam = Exam.builder()
			.name("주간테스트")
			.date(DATE)
			.countOfStudent(30)
			.classes(CLASSES)
			.build();

		EXAM = examRepository.save(exam);
	}

	@Test
	public void 성적등록() throws Exception {
	    // given
		String score = "{"
			+ "국어:90,"
			+ "수1:85,"
			+ "영어:80"
			+ "}";
		// when
		GradeDTO result = gradeService.create(MEMBER.getUserId(), EXAM.getDateName(), score);
		// then
		assertThat(result.getMemberExam()).isEqualTo(MEMBER.getUserId() + EXAM.getDateName());
		assertThat(result.getScore()).isEqualTo(score);
	}

	@Test
	public void 성적삭제() throws Exception {
	    // given
		String score = "{"
			+ "국어:90,"
			+ "수1:85,"
			+ "영어:80"
			+ "}";
	    // when
		gradeService.create(MEMBER.getUserId(), EXAM.getDateName(), score);
		gradeService.delete(MEMBER.getUserId() + EXAM.getDateName());
	    // then
		assertThrows(IllegalArgumentException.class, () ->
			gradeService.delete(MEMBER.getUserId() + EXAM.getDateName()));
	}

	@Test
	public void 성적변경() throws Exception {
		// given
		String score = "{"
			+ "국어:90,"
			+ "수1:85,"
			+ "영어:80"
			+ "}";
		String score2 = "{"
			+ "국어:80,"
			+ "수1:90,"
			+ "영어:75"
			+ "}";
		// when
		GradeDTO gradeDTO = gradeService.create(MEMBER.getUserId(), EXAM.getDateName(), score);
		GradeDTO result = gradeService.update(gradeDTO.getMemberExam(), score2);
		// then
		assertThat(result.getMemberExam()).isEqualTo(gradeDTO.getMemberExam());
		assertThat(result.getScore()).isEqualTo(score2);
	}

	@Test
	public void 사용자기준조회() throws Exception {
	    // given
		String score = "{"
			+ "국어:90,"
			+ "수1:85,"
			+ "영어:80"
			+ "}";
	    // when
		GradeDTO gradeDTO = gradeService.create(MEMBER.getUserId(), EXAM.getDateName(), score);
		List<GradeDTO> gradeDTOList = gradeService.readAllByMember(MEMBER.getUserId());
		// then
		GradeDTO result = gradeDTOList.get(0);
		assertThat(result.getMemberExam()).isEqualTo(gradeDTO.getMemberExam());
		assertThat(result.getScore()).isEqualTo(score);
	}

	@Test
	public void 시험기준조회() throws Exception {
	    // given
		String score = "{"
			+ "국어:90,"
			+ "수1:85,"
			+ "영어:80"
			+ "}";
	    // when
		GradeDTO gradeDTO = gradeService.create(MEMBER.getUserId(), EXAM.getDateName(), score);
		List<GradeDTO> gradeDTOList = gradeService.readAllByExam(EXAM.getDateName());
		// then
		GradeDTO result = gradeDTOList.get(0);
		assertThat(result.getMemberExam()).isEqualTo(gradeDTO.getMemberExam());
		assertThat(result.getScore()).isEqualTo(score);
	}
}