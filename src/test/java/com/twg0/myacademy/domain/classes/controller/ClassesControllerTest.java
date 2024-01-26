package com.twg0.myacademy.domain.classes.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.service.ClassesService;
import com.twg0.myacademy.domain.member.service.MemberService;

@WebMvcTest(ClassesController.class)
@MockBean(JpaMetamodelMappingContext.class) // JpaAuditing으로 인해 발생하는 오류 처리
class ClassesControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AcademyService academyService;

	@MockBean
	private MemberService memberService;

	@MockBean
	private ClassesService classesService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void 반조회API() throws Exception {
	    // given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(200)
				.teacher("kim")
				.build();

		final ClassesResponse classesResponse =
			ClassesResponse.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(200)
				.teacher("kim")
				.build();

		when(academyService.exist("seokang")).thenReturn(true);
		when(classesService.existByClassName("예비고1A")).thenReturn(true);
		when(classesService.read("예비고1A")).thenReturn(classesResponse);

	    // when
		ResultActions resultActions =
			mvc.perform(get("/academy/{academyUserId}/classes/{className}", "seokang", classesRequest.getClassName()))
				.andDo(print());

		// then
		resultActions
			.andExpect(status().isFound())
			.andExpect(jsonPath("subject").value("수학"))
			.andExpect(jsonPath("className").value("예비고1A"))
			.andExpect(jsonPath("countOfStudent").value(200))
			.andExpect(jsonPath("teacher").value("kim"));
	}

	@Test
	public void 반등록API() throws Exception {
	    // given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(200)
				.teacher("kim")
				.build();

		final ClassesResponse classesResponse =
			ClassesResponse.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(200)
				.teacher("kim")
				.build();

		when(academyService.exist("seokang")).thenReturn(true);
		when(classesService.existByClassName("예비고1A")).thenReturn(false);
		when(classesService.create(classesRequest, "seokang")).thenReturn(classesResponse);
	    // when
		ResultActions resultActions =
			mvc.perform(post("/academy/{academyUserId}/classes", "seokang")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(classesRequest)))
				.andDo(print());
	    // then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(jsonPath("subject").value("수학"))
			.andExpect(jsonPath("className").value("예비고1A"))
			.andExpect(jsonPath("countOfStudent").value(200))
			.andExpect(jsonPath("teacher").value("kim"));
	}

	@Test
	public void 반정보수정API() throws Exception {
	    // given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(200)
				.teacher("kim")
				.build();

		final ClassesRequest classesRequest2 =
			ClassesRequest.builder()
				.subject("과학")
				.className("예비고3")
				.countOfStudent(10)
				.teacher("Choi")
				.build();

		final ClassesResponse classesResponse =
			ClassesResponse.builder()
				.subject("과학")
				.className("예비고3")
				.countOfStudent(10)
				.teacher("Choi")
				.build();

		when(academyService.exist("seokang")).thenReturn(true);
		when(classesService.existByClassName("예비고1A")).thenReturn(true);
		when(classesService.updateInfo("예비고1A", classesRequest2)).thenReturn(classesResponse);
		// when
		ResultActions resultActions =
			mvc.perform(patch("/academy/{academyUserId}/classes/{className}", "seokang", "예비고1A")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(classesRequest2)))
				.andDo(print());
		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("subject").value("과학"))
			.andExpect(jsonPath("className").value("예비고3"))
			.andExpect(jsonPath("countOfStudent").value(10))
			.andExpect(jsonPath("teacher").value("Choi"));
	}

	@Test
	public void 반삭제API() throws Exception {
	    // given
		final ClassesRequest classesRequest =
			ClassesRequest.builder()
				.subject("수학")
				.className("예비고1A")
				.countOfStudent(200)
				.teacher("kim")
				.build();

		when(academyService.exist("seokang")).thenReturn(true);
		when(classesService.existByClassName("예비고1A")).thenReturn(true);
	    // when
		ResultActions resultActions =
			mvc.perform(delete("/academy/{academyUserId}/classes/{className}", "seokang", classesRequest.getClassName()))
				.andDo(print());
	    // then
		resultActions
			.andExpect(status().isOk());
	}

	/*
	 반-학생 컨트롤러
	 */
	@Test
	public void 학생등록API() throws Exception {
	    // given
		String academyUserId = "seokang";
		String className = "예비고1A과학";
		String memberUserId = "hong";

		final ClassesResponse classesResponse =
			ClassesResponse.builder()
				.subject("과학")
				.className("예비고1A과학")
				.countOfStudent(10)
				.teacher("Choi")
				.build();

		when(academyService.exist(academyUserId)).thenReturn(true);
		when(classesService.existByClassName(className)).thenReturn(true);
		when(memberService.exist(memberUserId)).thenReturn(true);
		when(classesService.existByClassNameAndMemberAndAcademyUserId(className, memberUserId, academyUserId)).thenReturn(false);
		when(classesService.register(className, memberUserId, academyUserId)).thenReturn(classesResponse);
	    // when

		ResultActions resultActions =
			mvc.perform(
					post("/academy/{academyUserId}/classes/{className}/member/{memberUserId}", academyUserId, className,
						memberUserId))
				.andDo(print());
	    // then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("subject").value("과학"))
			.andExpect(jsonPath("className").value("예비고1A과학"))
			.andExpect(jsonPath("countOfStudent").value(10))
			.andExpect(jsonPath("teacher").value("Choi"));
	}
}