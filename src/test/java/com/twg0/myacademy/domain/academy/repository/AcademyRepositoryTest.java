package com.twg0.myacademy.domain.academy.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.twg0.myacademy.domain.academy.entity.Academy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AcademyRepositoryTest {

	@Autowired
	private AcademyRepository academyRepository;

	@Test
	public void 학원등록() throws Exception {
	    // given
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyId("seokang")
			.password("tjrkd")
			.build();

	    // when
		final Academy result = academyRepository.save(academy);

	    // then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("서강학원");
		assertThat(result.getAddress()).isEqualTo("서울특별시 송파구 마천동");
		assertThat(result.getPhoneNumber()).isEqualTo("02-123-4567");
		assertThat(result.getStudentNumber()).isEqualTo(200);
		assertThat(result.getAcademyId()).isEqualTo("seokang");
		assertThat(result.getPassword()).isEqualTo("tjrkd");
	}

	@Test
	public void 학원조회테스트() throws Exception {
		// given
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyId("seokang")
			.password("tjrkd")
			.build();

		// when
		academyRepository.save(academy);
		final Academy result = academyRepository.findByAcademyId("seokang").get();

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("서강학원");
		assertThat(result.getAddress()).isEqualTo("서울특별시 송파구 마천동");
		assertThat(result.getPhoneNumber()).isEqualTo("02-123-4567");
		assertThat(result.getStudentNumber()).isEqualTo(200);
		assertThat(result.getAcademyId()).isEqualTo("seokang");
		assertThat(result.getPassword()).isEqualTo("tjrkd");
	}

	@Test
	public void 학원중복테스트() throws Exception {
		// given
		final Academy academy1 = Academy.builder()
			.name("서강학원중등관")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyId("seokang1")
			.password("tjrkd")
			.build();

		final Academy academy2 = Academy.builder()
			.name("서강학원고등관")
			.address("서울특별시 송파구 거여동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyId("seokang1")
			.password("tjrkd")
			.build();

		// when
		academyRepository.save(academy1);

		// then
		assertThrows(DataIntegrityViolationException.class,() -> academyRepository.save(academy2));
	}

	@Test
	public void 학원삭제테스트() throws Exception {
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyId("seokang")
			.password("tjrkd")
			.build();

		// when
		academyRepository.save(academy);
		academyRepository.deleteByAcademyId("seokang");
		Optional<Academy> result = academyRepository.findByAcademyId("seokang");

		// then
		assertThrows(NoSuchElementException.class, () -> result.get());
	}
}