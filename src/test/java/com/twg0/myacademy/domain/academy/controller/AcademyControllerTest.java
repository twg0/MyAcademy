package com.twg0.myacademy.domain.academy.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.twg0.myacademy.domain.academy.DTO.AcademyRequest;
import com.twg0.myacademy.domain.academy.DTO.AcademyResponse;
import com.twg0.myacademy.domain.academy.service.AcademyService;

@WebMvcTest(AcademyController.class)
@MockBean(JpaMetamodelMappingContext.class)
class AcademyControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AcademyService academyService;

	@Test
	public void 학원등록API() throws Exception {
	    // given
		final AcademyRequest academyRequest = AcademyRequest.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.password("asdf")
			.build();

		final AcademyResponse academyResponse = AcademyResponse.builder()
			.name("서강학원")
			.address("서울특별시 송파구 마천동")
			.phoneNumber("02-123-4567")
			.studentNumber(200)
			.academyUserId("seokang")
			.build();

		when(academyService.read(academyRequest.getAcademyUserId())).thenReturn(academyResponse);

	    // when
		ResultActions resultActions =
			mvc.perform(get("/academy/{academyUserId}", academyRequest.getAcademyUserId()))
				.andDo(print());

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value("서강학원"))
			.andExpect(jsonPath("address").value("서울특별시 송파구 마천동"))
			.andExpect(jsonPath("phoneNumber").value("02-123-4567"))
			.andExpect(jsonPath("studentNumber").value(200))
			.andExpect(jsonPath("academyUserId").value("seokang"));
	}
}