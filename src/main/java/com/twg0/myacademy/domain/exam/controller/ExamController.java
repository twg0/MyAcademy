package com.twg0.myacademy.domain.exam.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twg0.myacademy.domain.classes.service.ClassesService;
import com.twg0.myacademy.domain.exam.service.ExamService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("classes/{className}/Exam")
@RequiredArgsConstructor
public class ExamController {
	private final ExamService examService;
	private final ClassesService classesService;


}
