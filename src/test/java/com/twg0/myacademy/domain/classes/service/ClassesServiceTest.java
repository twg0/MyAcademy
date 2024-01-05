package com.twg0.myacademy.domain.classes.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;

@SpringBootTest
@Transactional
class ClassesServiceTest {
	@Autowired
	private ClassesService classesService;

	@Autowired
	private AcademyRepository academyRepository;

	private Academy ACADEMY;
	@BeforeEach
	public void setUp() {
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.password("tjrkd")
			.build();
		ACADEMY = academy;
		academyRepository.save(academy);
	}

	@Test
	public void 반등록() throws Exception {
	    // given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(0)
				.teacher("kim")
				.build();
	    // when
		ClassesResponse result = classesService.create(classesRequest, ACADEMY.getAcademyUserId());
		// then
		assertThat(result.getClassName()).isEqualTo(classesRequest.getClassName());
		assertThat(result.getSubject()).isEqualTo(classesRequest.getSubject());
		assertThat(result.getTeacher()).isEqualTo(classesRequest.getTeacher());
		assertThat(result.getCountOfStudent()).isEqualTo(classesRequest.getCountOfStudent());
	}

	@Test
	public void 반조회() throws Exception {
		// given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(0)
				.teacher("kim")
				.build();
		// when
		classesService.create(classesRequest, ACADEMY.getAcademyUserId());
		ClassesResponse result = classesService.read(classesRequest.getClassName());
		// then
		assertThat(result.getClassName()).isEqualTo(classesRequest.getClassName());
		assertThat(result.getSubject()).isEqualTo(classesRequest.getSubject());
		assertThat(result.getTeacher()).isEqualTo(classesRequest.getTeacher());
		assertThat(result.getCountOfStudent()).isEqualTo(classesRequest.getCountOfStudent());
	}

	@Test
	public void 반수정() throws Exception {
		// given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(0)
				.teacher("kim")
				.build();
		final ClassesRequest update =
			ClassesRequest.builder()
				.subject("국어")
				.className("예비고2B")
				.countOfStudent(0)
				.teacher("kim")
				.build();
		// when
		classesService.create(classesRequest, ACADEMY.getAcademyUserId());
		ClassesResponse result = classesService.updateInfo(classesRequest.getClassName(), update);
		// then
		assertThat(result.getClassName()).isEqualTo(update.getClassName());
		assertThat(result.getSubject()).isEqualTo(update.getSubject());
		assertThat(result.getTeacher()).isEqualTo(update.getTeacher());
		assertThat(result.getCountOfStudent()).isEqualTo(update.getCountOfStudent());
	}

	@Test
	public void 반중복() throws Exception {
		// given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(0)
				.teacher("kim")
				.build();
		final ClassesRequest classesRequest2 =
			ClassesRequest.builder()
				.subject("국어")
				.className("예비고1A")
				.countOfStudent(0)
				.teacher("kim")
				.build();
		// when
		classesService.create(classesRequest, ACADEMY.getAcademyUserId());
		// then
		assertThrows(DataIntegrityViolationException.class, () ->
			classesService.create(classesRequest2, ACADEMY.getAcademyUserId()));
	}

	@Test
	public void 반삭제() throws Exception {
		// given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(0)
				.teacher("kim")
				.build();
		// when
		classesService.create(classesRequest, ACADEMY.getAcademyUserId());
		classesService.delete(classesRequest.getClassName());
		// then
		assertThrows(NoSuchElementException.class, () ->
			classesService.read(classesRequest.getClassName()));
	}
}