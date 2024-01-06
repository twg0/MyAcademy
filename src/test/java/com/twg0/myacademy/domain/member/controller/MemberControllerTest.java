package com.twg0.myacademy.domain.member.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.service.ClassesService;
import com.twg0.myacademy.domain.member.DTO.MemberRequest;
import com.twg0.myacademy.domain.member.DTO.MemberResponse;
import com.twg0.myacademy.domain.member.service.MemberService;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class) // JpaAuditing으로 인해 발생하는 오류 처리
class MemberControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private AcademyService academyService;

	@MockBean
	private MemberService memberService;

	@Autowired
	private ObjectMapper objectMapper;

	private final LocalDateTime BIRTH = LocalDateTime.of(1996, 8, 25, 0, 0,0);
	private final String birth = BIRTH.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

	@Test
	public void 학원사용자조회API() throws Exception {
	    // given
		final MemberRequest memberRequest = MemberRequest.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.build();

		final MemberResponse memberResponse = MemberResponse.builder()
			.username("홍길동")
			.userId("hong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.build();

	    // when
		when(academyService.exist("seokang")).thenReturn(true);
		when(memberService.exist("hong")).thenReturn(true);
		when(memberService.read("hong")).thenReturn(memberResponse);

		// when
		ResultActions resultActions =
			mvc.perform(get("/academy/{academyUserId}/member/{memberUserId}", "seokang", memberRequest.getUserId()))
				.andDo(print());

		// then
		resultActions
			.andExpect(status().isFound())
			.andExpect(jsonPath("username").value("홍길동"))
			.andExpect(jsonPath("userId").value("hong"))
			.andExpect(jsonPath("age").value(18))
			.andExpect(jsonPath("birth").value(birth))
			.andExpect(jsonPath("school").value("방산"));
	}
}