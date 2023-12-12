package com.twg0.myacademy.domain.academy.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.twg0.myacademy.domain.academy.DTO.AcademyDTO;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AcademyServiceTest {
	@Autowired
	private AcademyRepository academyRepository;
	@Autowired
	private AcademyService academyService;

	@Test
	public void 학원등록테스트() throws Exception {
	    // given
		final AcademyDTO academyDTO = AcademyDTO.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.userId("seokang")
			.password("tjrkd")
			.build();
	    // when
		AcademyDTO result = academyService.create(academyDTO);
		// then
		assertThat(result.getName()).isEqualTo(academyDTO.getName());
		assertThat(result.getAddress()).isEqualTo(academyDTO.getAddress());
		assertThat(result.getPhoneNumber()).isEqualTo(academyDTO.getPhoneNumber());
		assertThat(result.getStudentNumber()).isEqualTo(academyDTO.getStudentNumber());
		assertThat(result.getUserId()).isEqualTo(academyDTO.getUserId());
		assertThat(result.getPassword()).isEqualTo(academyDTO.getPassword());
	}
}