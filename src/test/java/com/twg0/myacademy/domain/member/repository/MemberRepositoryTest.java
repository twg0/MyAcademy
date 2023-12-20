package com.twg0.myacademy.domain.member.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.twg0.myacademy.domain.academy.entity.Academy;
import com.twg0.myacademy.domain.academy.repository.AcademyRepository;
import com.twg0.myacademy.domain.member.entity.Member;
import com.twg0.myacademy.domain.member.enums.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

	@Autowired
	private AcademyRepository academyRepository;

	@Autowired
	private MemberRepository memberRepository;

	private Academy ACADEMY;

	private Instant BIRTH;

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
		ACADEMY = academy;
		academyRepository.save(academy);
		BIRTH = LocalDateTime.of(1996, 8, 25, 0, 0).atZone(ZoneId.of("Asia/Seoul")).toInstant();
	}

	@Test
	public void 멤버등록테스트() throws Exception {
	    // given
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

	    // when
		Member result = memberRepository.save(member);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getUsername()).isEqualTo("홍길동");
		assertThat(result.getUserId()).isEqualTo("hong");
		assertThat(result.getPassword()).isEqualTo("gildong");
		assertThat(result.getAge()).isEqualTo(18);
		assertThat(result.getBirth()).isEqualTo(BIRTH);
		assertThat(result.getSchool()).isEqualTo("방산");
		assertThat(result.getAcademy().getUserId()).isEqualTo("seokang");
	}

	@Test
	public void 멤버조회테스트() throws Exception {
		// given
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

		// when
		memberRepository.save(member);
		final Member result = memberRepository.findByUserId("hong").get();

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getUsername()).isEqualTo("홍길동");
		assertThat(result.getUserId()).isEqualTo("hong");
		assertThat(result.getPassword()).isEqualTo("gildong");
		assertThat(result.getAge()).isEqualTo(18);
		assertThat(result.getBirth()).isEqualTo(BIRTH);
		assertThat(result.getSchool()).isEqualTo("방산");
		assertThat(result.getAcademy().getUserId()).isEqualTo("seokang");
	}

	@Test
	public void 멤버중복테스트() throws Exception {
		// given
		final Member member1 = Member.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.role(Role.MEMBER)
			.academy(ACADEMY)
			.build();

		final Member member2 = Member.builder()
			.username("홍범도")
			.userId("hong")
			.password("beomdo")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.academy(ACADEMY)
			.build();

		// when
		memberRepository.save(member1);

	    // then
		assertThrows(DataIntegrityViolationException.class, () -> memberRepository.save(member2));
	}

	@Test
	public void 멤버삭제테스트() throws Exception {
		// given
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

		// when
		memberRepository.save(member);
	    memberRepository.deleteByUserId("hong");
		Optional<Member> result = memberRepository.findByUserId("hong");
		// then
		assertThrows(NoSuchElementException.class, ()->result.get());
	}
}