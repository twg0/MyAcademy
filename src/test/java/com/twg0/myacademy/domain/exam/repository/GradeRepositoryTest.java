package com.twg0.myacademy.domain.exam.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.exam.entity.Exam;
import com.twg0.myacademy.domain.exam.entity.Grade;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GradeRepositoryTest {

	@Autowired
	private GradeRepository gradeRepository;
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
	private Instant BIRTH;
	private Classes CLASSES;
	private Exam EXAM;
	private Instant DATE;
	@BeforeEach
	public void setUp() {
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.userId("seokang")
			.password("tjrkd")
			.build();
		ACADEMY = academyRepository.save(academy);

		BIRTH = LocalDateTime.of(1996, 8, 25, 0, 0).atZone(ZoneId.of("Asia/Seoul")).toInstant();
		DATE = LocalDateTime.of(2023, 11, 11, 15, 30).atZone(ZoneId.of("Asia/Seoul")).toInstant();

		final Member member = Member.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
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
	public void 성적등록테스트() throws Exception {
	    // given
		final Grade grade = Grade.builder()
			.score("{"
				+ "국어:90,"
				+ "수1:85,"
				+ "영어:80"
				+ "}")
			.member(MEMBER)
			.exam(EXAM)
			.build();
	    // when
		Grade result = gradeRepository.save(grade);
		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getScore()).isEqualTo(grade.getScore());
		assertThat(result.getMemberExam()).isEqualTo(grade.getMemberExam());
	}
}