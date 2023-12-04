package com.twg0.myacademy.domain.academy.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
	public void create() throws Exception {
	    // given

	    // when

	    // then

	}
}