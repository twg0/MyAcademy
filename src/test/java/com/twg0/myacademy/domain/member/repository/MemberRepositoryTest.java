package com.twg0.myacademy.domain.member.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.twg0.myacademy.domain.member.entity.Member;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void 멤버등록테스트() throws Exception {
	    // given
		final Member member = Member.builder()
			.username("홍길동")
			.age(18)
			.birth(LocalDateTime.of(1996, 8, 25, 0, 0).atZone(ZoneId.of("Asia/Seoul")).toInstant())
			.school("방산")
			.academy(null)
			.build();

	    // when
		Member result = memberRepository.save(member);
		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getUsername()).isEqualTo("홍길동");
		assertThat(result.getAge()).isEqualTo(18);
		assertThat(result.getBirth()).isEqualTo(LocalDateTime.of(1996, 8, 25, 0, 0).atZone(ZoneId.of("Asia/Seoul")).toInstant());
		assertThat(result.getSchool()).isEqualTo("방산");
		assertThat(result.getAcademy()).isNull();
	}
}