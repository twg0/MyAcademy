package com.twg0.myacademy.domain.academy.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.DTO.AcademyRequest;
import com.twg0.myacademy.domain.academy.DTO.AcademyResponse;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AcademyServiceTest {
	@Autowired
	private AcademyService academyService;

	@Test
	public void 학원등록테스트() throws Exception {
	    // given
		final AcademyRequest academyRequest = AcademyRequest.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.password("asdf")
			.build();
	    // when
		AcademyResponse result = academyService.create(academyRequest);
		// then
		assertThat(result.getName()).isEqualTo(academyRequest.getName());
		assertThat(result.getAddress()).isEqualTo(academyRequest.getAddress());
		assertThat(result.getPhoneNumber()).isEqualTo(academyRequest.getPhoneNumber());
		assertThat(result.getStudentNumber()).isEqualTo(academyRequest.getStudentNumber());
		assertThat(result.getAcademyUserId()).isEqualTo(academyRequest.getAcademyUserId());
	}

	@Test
	public void 학원조회테스트() throws Exception {
		// given
		final AcademyRequest academyRequest = AcademyRequest.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.password("asdf")
			.build();
		// when
		academyService.create(academyRequest);
		AcademyResponse result = academyService.read(academyRequest.getAcademyUserId());
	    // then
		assertThat(result.getName()).isEqualTo(academyRequest.getName());
		assertThat(result.getAddress()).isEqualTo(academyRequest.getAddress());
		assertThat(result.getPhoneNumber()).isEqualTo(academyRequest.getPhoneNumber());
		assertThat(result.getStudentNumber()).isEqualTo(academyRequest.getStudentNumber());
		assertThat(result.getAcademyUserId()).isEqualTo(academyRequest.getAcademyUserId());
	}

	@Test
	public void 학원중복테스트() throws Exception {
	    // given
		final AcademyRequest academyRequest = AcademyRequest.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.password("asdf")
			.build();
		final AcademyRequest academyRequest2 = AcademyRequest.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.password("asdf")
			.build();
	    // when
		academyService.create(academyRequest);
	    // then
		assertThrows(DataIntegrityViolationException.class,
			() -> academyService.create(academyRequest2));
	}

	@Test
	public void 학원정보수정() throws Exception {
	    // given
		final AcademyRequest academyRequest = AcademyRequest.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.password("asdf")
			.build();
		final AcademyRequest academyRequest2 = AcademyRequest.builder()
			.name("서강학원중등관")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.build();
	    // when
		AcademyResponse academyResponse = academyService.create(academyRequest);
		AcademyResponse result = academyService.updateInfo(academyResponse.getAcademyUserId(), academyRequest2);
		// then
		assertThat(result.getName()).isEqualTo(academyRequest2.getName());
		assertThat(result.getAddress()).isEqualTo(academyRequest2.getAddress());
		assertThat(result.getPhoneNumber()).isEqualTo(academyRequest2.getPhoneNumber());
		assertThat(result.getStudentNumber()).isEqualTo(academyRequest2.getStudentNumber());
	}

	@Test
	public void 학원삭제() throws Exception {
	    // given
		final AcademyRequest academyRequest = AcademyRequest.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.password("asdf")
			.build();
	    // when
		AcademyResponse academyResponse = academyService.create(academyRequest);
		academyService.delete(academyResponse.getAcademyUserId());
		// then
		assertThrows(NoSuchElementException.class,
			() -> academyService.read(academyRequest.getAcademyUserId()));
	}
}