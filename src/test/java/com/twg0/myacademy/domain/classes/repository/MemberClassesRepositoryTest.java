package com.twg0.myacademy.domain.classes.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DuplicateKeyException;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.enums.Role;
import com.twg0.myacademy.domain.member.repository.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberClassesRepositoryTest {

	@Autowired
	private MemberClassesRepository memberClassesRepository;
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
			.academyUserId("seokang")
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
	public void 멤버반등록() throws Exception {
		// given
		final MemberClasses memberClasses = MemberClasses.builder()
			.classes(CLASSES)
			.member(MEMBER)
			.build();
		// when
		MemberClasses result = memberClassesRepository.save(memberClasses);
		// then
		assertThat(result.getClasses()).isEqualTo(memberClasses.getClasses());
		assertThat(result.getMember()).isEqualTo(memberClasses.getMember());
	}

	@Test
	public void 멤버기준조회() throws Exception {
		// given
		final MemberClasses memberClasses = MemberClasses.builder()
			.classes(CLASSES)
			.member(MEMBER)
			.build();

		// when
		memberClassesRepository.save(memberClasses);
		List<MemberClasses> memberClassesList = memberClassesRepository.findByMember(MEMBER);

		// then
		assertThat(memberClassesList.contains(memberClasses)).isTrue();
	}

	@Test
	public void 반기준조회() throws Exception {
		// given
		final MemberClasses memberClasses = MemberClasses.builder()
			.classes(CLASSES)
			.member(MEMBER)
			.build();

		// when
		memberClassesRepository.save(memberClasses);
		List<MemberClasses> memberClassesList = memberClassesRepository.findByClasses(CLASSES);
		// then
		assertThat(memberClassesList.contains(memberClasses)).isTrue();

	}

	@Test
	public void 멤버반중복테스트() throws Exception {
		// given
		final MemberClasses memberClasses1 = MemberClasses.builder()
			.classes(CLASSES)
			.member(MEMBER)
			.build();

		final MemberClasses memberClasses2 = MemberClasses.builder()
			.classes(CLASSES)
			.member(MEMBER)
			.build();

		// when
		memberClassesRepository.save(memberClasses1);
		// then
		assertThrows(DuplicateKeyException.class, () -> memberClassesRepository.save(memberClasses2));

	}

	@Test
	public void 멤버반삭제() throws Exception {
		// given
		final MemberClasses memberClasses = MemberClasses.builder()
			.classes(CLASSES)
			.member(MEMBER)
			.build();

		// when
		memberClassesRepository.save(memberClasses);
		memberClassesRepository.deleteByMemberAndClasses(MEMBER, CLASSES);
		Optional<MemberClasses> result = memberClassesRepository.findByMemberAndClasses(MEMBER, CLASSES);

		// then
		assertThrows(NoSuchElementException.class, () -> result.get());

	}
}