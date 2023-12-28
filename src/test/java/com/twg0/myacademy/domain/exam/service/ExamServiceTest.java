package com.twg0.myacademy.domain.exam.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.exam.DTO.ExamRequest;
import com.twg0.myacademy.domain.exam.DTO.ExamResponse;

@SpringBootTest
@Transactional
class ExamServiceTest {
	@Autowired
	private ExamService examService;
	@Autowired
	private AcademyRepository academyRepository;
	@Autowired
	private ClassesRepository classesRepository;

	private Academy ACADEMY;
	private Classes CLASSES;
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

		DATE = LocalDateTime.of(2023, 11, 11, 15, 30).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

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
	public void 시험등록() throws Exception {
	    // given
		final ExamRequest examRequest = ExamRequest.builder()
			.date(DATE)
			.name("주간테스트")
			.countOfStudent(10)
			.build();
	    // when
		ExamResponse result = examService.create(CLASSES.getClassName(), examRequest);
		// then
		assertThat(result.getName()).isEqualTo(examRequest.getName());
		assertThat(result.getDate()).isEqualTo(examRequest.getDate());
		assertThat(result.getCountOfStudent()).isEqualTo(examRequest.getCountOfStudent());
	}

	@Test
	public void 시험수정() throws Exception {
	    // given
		final ExamRequest examRequest = ExamRequest.builder()
			.date(DATE)
			.name("주간테스트")
			.countOfStudent(10)
			.build();
		final ExamRequest examRequest2 = ExamRequest.builder()
			.date(DATE)
			.name("일간테스트")
			.countOfStudent(20)
			.build();
	    // when
		examService.create(CLASSES.getClassName(), examRequest);
		ExamResponse result = examService.updateInfo(examRequest2, examRequest.getDateName());
		// then
		assertThat(result.getName()).isEqualTo(examRequest2.getName());
		assertThat(result.getDate()).isEqualTo(examRequest2.getDate());
		assertThat(result.getCountOfStudent()).isEqualTo(examRequest2.getCountOfStudent());
	}

	@Test
	public void 시험조회() throws Exception {
	    // given
		final ExamRequest examRequest = ExamRequest.builder()
			.date(DATE)
			.name("주간테스트")
			.countOfStudent(10)
			.build();
	    // when
		examService.create(CLASSES.getClassName(), examRequest);
		ExamResponse result = examService.read(examRequest.getDateName());
		// then
		assertThat(result.getName()).isEqualTo(examRequest.getName());
		assertThat(result.getDate()).isEqualTo(examRequest.getDate());
		assertThat(result.getCountOfStudent()).isEqualTo(examRequest.getCountOfStudent());
	}

	@Test
	public void 시험삭제() throws Exception {
		// given
		final ExamRequest examRequest = ExamRequest.builder()
			.date(DATE)
			.name("주간테스트")
			.countOfStudent(10)
			.build();
		// when
		examService.create(CLASSES.getClassName(), examRequest);
	    examService.delete(examRequest.getDateName());
	    // then
		assertThrows(IllegalArgumentException.class, () ->
			examService.delete(examRequest.getDateName()));
	}
}