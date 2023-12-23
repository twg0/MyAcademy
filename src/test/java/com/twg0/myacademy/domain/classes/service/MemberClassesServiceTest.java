package com.twg0.myacademy.domain.classes.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.DTO.MemberClassesDTO;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.repository.ClassesRepository;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.enums.Role;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberClassesServiceTest {

	@Autowired
	private MemberClassesService memberClassesService;
	@Autowired
	private AcademyRepository academyRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ClassesRepository classesRepository;

	private Academy ACADEMY;
	private Member MEMBER;
	private LocalDateTime BIRTH;
	private Classes CLASSES;
	@BeforeEach
	public void setUp() {
		final Academy academy = Academy.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.userId("seokang")
			.password("tjrkd")
			.build();
		ACADEMY = academyRepository.save(academy);

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

		final Classes classes = Classes.builder()
			.subject("수학")
			.className("예비고1A")
			.countOfStudent(0)
			.teacher("kim")
			.academy(ACADEMY)
			.build();

		MEMBER = memberRepository.save(member);
		CLASSES = classesRepository.save(classes);
	}

	@Test
	public void 수강등록() throws Exception {
	    // given
		String userId = MEMBER.getUserId();
		String className = CLASSES.getClassName();
		// when
		MemberClassesDTO result = memberClassesService.create(userId, className);
		// then
		assertThat(result.getMember()).isEqualTo(MEMBER);
		assertThat(result.getClasses()).isEqualTo(CLASSES);
	}

	@Test
	public void 수강삭제() throws Exception {
		// given
		String userId = MEMBER.getUserId();
		String className = CLASSES.getClassName();
		// when
		memberClassesService.create(userId, className);
		memberClassesService.delete(userId, className);
		// then
		assertThrows(IllegalArgumentException.class, () ->
			memberClassesService.delete(userId, className));
	}

	@Test
	public void 멤버수강내역조회() throws Exception {
	    // given
		String userId = MEMBER.getUserId();
		String className = CLASSES.getClassName();
		memberClassesService.create(userId, className);
	    // when
		List<MemberClassesDTO> list = memberClassesService.readAllByMember(userId);
		// then
		assertThat(userId).isEqualTo(list.get(0).getMember().getUserId());
		assertThat(className).isEqualTo(list.get(0).getClasses().getClassName());
	}

	@Test
	public void 반수강자조회() throws Exception {
		// given
		String userId = MEMBER.getUserId();
		String className = CLASSES.getClassName();
		memberClassesService.create(userId, className);
		// when
		List<MemberClassesDTO> list = memberClassesService.readAllByClasses(className);
		// then
		assertThat(userId).isEqualTo(list.get(0).getMember().getUserId());
		assertThat(className).isEqualTo(list.get(0).getClasses().getClassName());

	}
}