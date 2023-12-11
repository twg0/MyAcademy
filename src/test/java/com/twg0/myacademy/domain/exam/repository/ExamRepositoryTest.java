package com.twg0.myacademy.domain.exam.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.exam.entity.Exam;
import com.twg0.myacademy.domain.member.entity.Member;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExamRepositoryTest {

	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private AcademyRepository academyRepository;
	@Autowired
	private ClassesRepository classesRepository;

	private Academy ACADEMY;
	private Classes CLASSES;
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

		DATE = LocalDateTime.of(2023, 11, 11, 15, 30).atZone(ZoneId.of("Asia/Seoul")).toInstant();

		final Classes classes = Classes.builder()
			.subject("수학")
			.className("예비고1A")
			.countOfStudent(0)
			.teacher("kim")
			.academy(ACADEMY)
			.build();

		CLASSES = classesRepository.save(classes);
	}


	@Test
	public void 시험등록테스트() throws Exception {
	    // given
	    final Exam exam = Exam.builder()
			.name("주간테스트")
			.date(DATE)
			.countOfStudent(30)
			.classes(CLASSES)
			.build();
	    // when
		Exam result = examRepository.save(exam);
		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getDate()).isEqualTo(exam.getDate());
		assertThat(result.getDate()).isEqualTo(exam.getDate());
		assertThat(result.getClasses()).isEqualTo(exam.getClasses());
		assertThat(result.getCountOfStudent()).isEqualTo(exam.getCountOfStudent());
	}

	@Test
	public void 시험조회테스트() throws Exception {
		// given
		final Exam exam = Exam.builder()
			.name("주간테스트")
			.date(DATE)
			.countOfStudent(30)
			.classes(CLASSES)
			.build();
		// when
		examRepository.save(exam);
		Exam result = examRepository.findByNameAndDate(exam.getName(), DATE).get();
		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getDate()).isEqualTo(exam.getDate());
		assertThat(result.getDate()).isEqualTo(exam.getDate());
		assertThat(result.getClasses()).isEqualTo(exam.getClasses());
		assertThat(result.getCountOfStudent()).isEqualTo(exam.getCountOfStudent());
	}
}