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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.service.ClassesService;
import com.twg0.myacademy.domain.member.DTO.MemberRequest;
import com.twg0.myacademy.domain.member.DTO.MemberResponse;
import com.twg0.myacademy.domain.member.enums.Role;
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

	@Test
	public void 학원사용자생성API() throws Exception {
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

		when(academyService.exist("seokang")).thenReturn(true);
		when(memberService.exist("hong")).thenReturn(false);
		when(memberService.create(memberRequest, "seokang", Role.MEMBER)).thenReturn(memberResponse);

		// when
		ResultActions resultActions =
			mvc.perform(post("/academy/{academyUserId}/member", "seokang")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(memberRequest)))
				.andDo(print());

		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(jsonPath("username").value("홍길동"))
			.andExpect(jsonPath("userId").value("hong"))
			.andExpect(jsonPath("age").value(18))
			.andExpect(jsonPath("birth").value(birth))
			.andExpect(jsonPath("school").value("방산"));
	}

	@Test
	public void 학원사용자정보수정API() throws Exception {
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
			.age(20)
			.birth(BIRTH)
			.school("방산")
			.build();

		final MemberResponse memberResponse = MemberResponse.builder()
			.username("고길동")
			.userId("go")
			.age(20)
			.birth(BIRTH)
			.school("방산")
			.build();

		when(academyService.exist("seokang")).thenReturn(true);
		when(memberService.exist("hong")).thenReturn(true);
		when(memberService.exist("go")).thenReturn(false);
		when(memberService.updateInfo(memberRequest2, "hong")).thenReturn(memberResponse);

		// when
		ResultActions resultActions =
			mvc.perform(patch("/academy/{academyUserId}/member/{memberUserId}", "seokang", memberRequest.getUserId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(memberRequest2)))
				.andDo(print());

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("username").value("고길동"))
			.andExpect(jsonPath("userId").value("go"))
			.andExpect(jsonPath("age").value(20))
			.andExpect(jsonPath("birth").value(birth))
			.andExpect(jsonPath("school").value("방산"));
	}

	@Test
	public void 학원사용자삭제API() throws Exception {
	    // given
		final MemberRequest memberRequest = MemberRequest.builder()
			.username("홍길동")
			.userId("hong")
			.password("gildong")
			.age(18)
			.birth(BIRTH)
			.school("방산")
			.build();

		when(academyService.exist("seokang")).thenReturn(true);
		when(memberService.exist("hong")).thenReturn(true);
		// when
		ResultActions resultActions =
			mvc.perform(delete("/academy/{academyUserId}/member/{memberUserId}", "seokang", memberRequest.getUserId()))
				.andDo(print());

		// then
		resultActions
			.andExpect(status().isOk());
	}
}