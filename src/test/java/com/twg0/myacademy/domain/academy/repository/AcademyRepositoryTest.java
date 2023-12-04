package com.twg0.myacademy.domain.academy.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.twg0.myacademy.domain.academy.entity.Academy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AcademyRepositoryTest {

	@Autowired
	private AcademyRepository academyRepository;

	@Test
	public void AcademyRepository가Null이아님() throws Exception {
	    // given
	    // when
	    // then
		assertThat(academyRepository).isNotNull();
	}

	@Test
	public void 학원등록() throws Exception {
	    // given
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.build();

	    // when
		final Academy result = academyRepository.save(academy);

	    // then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("서강학원");
		assertThat(result.getAddress()).isEqualTo("서울특별시 송파구 마천동");
		assertThat(result.getPhoneNumber()).isEqualTo("02-123-4567");
		assertThat(result.getStudentNumber()).isEqualTo(200);
	}
}