package com.twg0.myacademy.domain.classes.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClassesRepositoryTest {

	@Autowired
	private ClassesRepository classesRepository;
	@Autowired
	private AcademyRepository academyRepository;

	private Academy ACADEMY;
	@BeforeEach
	public void setUp(){
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.userId("seokang")
			.password("tjrkd")
			.build();
		ACADEMY = academy;
		academyRepository.save(academy);
	}

	@Test
	public void 반등록테스트() throws Exception {
		// given
		final Classes classes = Classes.builder()
			.subject("수학")
			.className("예비고1A")
			.countOfStudent(0)
			.teacher("kim")
			.academy(ACADEMY)
			.build();

	    // when
		Classes result = classesRepository.save(classes);
		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getSubject()).isEqualTo("수학");
		assertThat(result.getClassName()).isEqualTo("예비고1A");
		assertThat(result.getCountOfStudent()).isEqualTo(0);
		assertThat(result.getTeacher()).isEqualTo("kim");
		assertThat(result.getAcademy()).isNotNull();
	}

	@Test
	public void 반조회테스트() throws Exception {
		// given
		final Classes classes = Classes.builder()
			.subject("수학")
			.className("예비고1A")
			.countOfStudent(0)
			.teacher("kim")
			.academy(ACADEMY)
			.build();

		// when
		classesRepository.save(classes);
		Classes result = classesRepository.findByClassName(classes.getClassName()).get();
		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getSubject()).isEqualTo(classes.getSubject());
		assertThat(result.getClassName()).isEqualTo(classes.getClassName());
		assertThat(result.getCountOfStudent()).isEqualTo(classes.getCountOfStudent());
		assertThat(result.getTeacher()).isEqualTo(classes.getTeacher());
		assertThat(result.getAcademy()).isNotNull();
	}

	@Test
	public void 반중복테스트() throws Exception {
		// given
		final Classes classes1 = Classes.builder()
			.subject("수학")
			.className("예비고1A")
			.countOfStudent(0)
			.teacher("kim")
			.academy(ACADEMY)
			.build();

		final Classes classes2 = Classes.builder()
			.subject("과학")
			.className("예비고1A")
			.countOfStudent(0)
			.teacher("seo")
			.academy(ACADEMY)
			.build();

		// when
		classesRepository.save(classes1);
		// then
		assertThrows(DataIntegrityViolationException.class, () -> classesRepository.save(classes2));
	}

	@Test
	public void 반삭제테스트() throws Exception {
		// given
		final Classes classes = Classes.builder()
			.subject("수학")
			.className("예비고1A")
			.countOfStudent(0)
			.teacher("kim")
			.academy(ACADEMY)
			.build();

		// when
		classesRepository.save(classes);
		classesRepository.deleteByClassName(classes.getClassName());
		Optional<Classes> result = classesRepository.findByClassName(classes.getClassName());
		// then
		assertThrows(NoSuchElementException.class, () -> result.get());
	}
}