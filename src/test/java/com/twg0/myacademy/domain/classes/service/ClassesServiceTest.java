package com.twg0.myacademy.domain.classes.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
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
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.enums.Role;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

@SpringBootTest
@Transactional
class ClassesServiceTest {
	@Autowired
	private ClassesService classesService;

	@Autowired
	private AcademyRepository academyRepository;

	@Autowired
	private MemberRepository memberRepository;

	private Academy ACADEMY;
	private Member MEMBER;
	private LocalDateTime BIRTH;
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

		BIRTH = LocalDateTime.of(1996, 8, 25, 0, 0).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

		final Member member = Member.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.role(Role.MEMBER)
			.academy(ACADEMY)
			.build();

		MEMBER = memberRepository.save(member);
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

	@Test
	public void 반학생등록() throws Exception {
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(0)
				.teacher("kim")
				.build();
		// when
		classesService.create(classesRequest, ACADEMY.getAcademyUserId());
		ClassesResponse response = classesService.register(classesRequest.getClassName(), MEMBER.getUserId(), ACADEMY.getAcademyUserId());
		// then
		List<MemberClasses> memberClasses = MEMBER.getMemberClasses();
		assertThat(memberClasses.get(0).getClasses().getClassName()).isEqualTo(response.getClassName());
	}

	@Test
	public void 반학생삭제() throws Exception {
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(0)
				.teacher("kim")
				.build();
		// when
		classesService.create(classesRequest, ACADEMY.getAcademyUserId());
		classesService.register(classesRequest.getClassName(), MEMBER.getUserId(), ACADEMY.getAcademyUserId());
		classesService.deleteMember(classesRequest.getClassName(), MEMBER.getUserId(), ACADEMY.getAcademyUserId());
		// then
		assertThat(MEMBER.getMemberClasses().size()).isEqualTo(0);
	}

	@Test
	public void 학생이속한반조회() throws Exception {
	    // given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A수학")
				.countOfStudent(0)
				.teacher("kim")
				.build();

		final ClassesRequest classesRequest2 =
			ClassesRequest.builder()
				.subject("영어")
				.className("예비고1A영어")
				.countOfStudent(0)
				.teacher("kim")
				.build();

		final ClassesRequest classesRequest3 =
			ClassesRequest.builder()
				.subject("과학")
				.className("예비고1A과학")
				.countOfStudent(0)
				.teacher("kim")
				.build();
	    // when
		ClassesResponse classesResponse = classesService.create(classesRequest, ACADEMY.getAcademyUserId());
		ClassesResponse classesResponse1 = classesService.create(classesRequest2, ACADEMY.getAcademyUserId());
		ClassesResponse classesResponse2 = classesService.create(classesRequest3, ACADEMY.getAcademyUserId());

		System.out.println("학생등록");
		classesService.register(classesRequest.getClassName(), MEMBER.getUserId(), ACADEMY.getAcademyUserId());
		classesService.register(classesRequest2.getClassName(), MEMBER.getUserId(), ACADEMY.getAcademyUserId());
		classesService.register(classesRequest3.getClassName(), MEMBER.getUserId(), ACADEMY.getAcademyUserId());

	    // then
		System.out.println("학생조회");
		List<ClassesResponse> allByMemerUserId = classesService.findAllByMemerUserId(MEMBER.getUserId());
		assertThat(allByMemerUserId.contains(classesResponse)).isTrue();
		assertThat(allByMemerUserId.contains(classesResponse1)).isTrue();
		assertThat(allByMemerUserId.contains(classesResponse2)).isTrue();
	}
}