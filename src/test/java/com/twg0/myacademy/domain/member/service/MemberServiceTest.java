package com.twg0.myacademy.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.member.DTO.MemberRequest;
import com.twg0.myacademy.domain.member.DTO.MemberResponse;
import com.twg0.myacademy.domain.member.enums.Role;

@SpringBootTest
@Transactional
class MemberServiceTest {
	@Autowired
	private MemberService memberService;

	@Autowired
	private AcademyRepository academyRepository;

	private Academy ACADEMY;
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
	}

	@Test
	public void 멤버등록() throws Exception {
	    // given
	    final MemberRequest memberRequest = MemberRequest.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.build();
	    // when
		MemberResponse result = memberService.create(memberRequest, ACADEMY.getAcademyUserId(), Role.MEMBER);
		// then
		assertThat(result.getUsername()).isEqualTo(memberRequest.getUsername());
		assertThat(result.getUserId()).isEqualTo(memberRequest.getUserId());
		assertThat(result.getAge()).isEqualTo(memberRequest.getAge());
		assertThat(result.getBirth()).isEqualTo(memberRequest.getBirth());
		assertThat(result.getSchool()).isEqualTo(memberRequest.getSchool());
	}

	@Test
	public void 멤버수정() throws Exception {
	    // given
		final MemberRequest memberRequest = MemberRequest.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.build();
		final MemberRequest memberRequest2 = MemberRequest.builder()
			.username("고길동")
			.userId("go")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("오금")
			.build();
	    // when
		memberService.create(memberRequest, ACADEMY.getAcademyUserId(), Role.MEMBER);
		MemberResponse result = memberService.updateInfo(memberRequest2, memberRequest.getUserId());
		// then
		assertThat(result.getUsername()).isEqualTo(memberRequest2.getUsername());
		assertThat(result.getUserId()).isEqualTo(memberRequest2.getUserId());
		assertThat(result.getAge()).isEqualTo(memberRequest2.getAge());
		assertThat(result.getBirth()).isEqualTo(memberRequest2.getBirth());
		assertThat(result.getSchool()).isEqualTo(memberRequest2.getSchool());
	}

	@Test
	public void 멤버조회() throws Exception {
	    // given
		final MemberRequest memberRequest = MemberRequest.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.build();
	    // when
		memberService.create(memberRequest, ACADEMY.getAcademyUserId(), Role.MEMBER);
		MemberResponse result = memberService.read(memberRequest.getUserId());
		// then
		assertThat(result.getUsername()).isEqualTo(memberRequest.getUsername());
		assertThat(result.getUserId()).isEqualTo(memberRequest.getUserId());
		assertThat(result.getAge()).isEqualTo(memberRequest.getAge());
		assertThat(result.getBirth()).isEqualTo(memberRequest.getBirth());
		assertThat(result.getSchool()).isEqualTo(memberRequest.getSchool());
	}

	@Test
	public void 멤버삭제() throws Exception {
	    // given
		final MemberRequest memberRequest = MemberRequest.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.build();
	    // when
		memberService.create(memberRequest, ACADEMY.getAcademyUserId(), Role.MEMBER);
		memberService.delete(memberRequest.getUserId());
	    // then
		assertThrows(NoSuchElementException.class, () ->
			memberService.read(memberRequest.getUserId()));
	}
}