package com.twg0.myacademy.domain.classes.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.DTO.ClassesResponse;
import com.twg0.myacademy.domain.classes.service.ClassesService;

@WebMvcTest(ClassesController.class)
@MockBean(JpaMetamodelMappingContext.class) // JpaAuditing으로 인해 발생하는 오류 처리
class ClassesControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AcademyService academyService;

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
}