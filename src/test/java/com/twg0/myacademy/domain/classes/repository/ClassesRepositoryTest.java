package com.twg0.myacademy.domain.classes.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClassesRepositoryTest {

	@Autowired
	private ClassesRepository classesRepository;

	@Test
	public void ClassesRepository가Null이아님() throws Exception {
		// given
		// when
		// then
		assertThat(classesRepository).isNotNull();
	}
}