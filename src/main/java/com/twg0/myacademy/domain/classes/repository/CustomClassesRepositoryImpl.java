package com.twg0.myacademy.domain.classes.repository;

import static com.twg0.myacademy.domain.classes.entity.QClasses.*;
import static com.twg0.myacademy.domain.classes.entity.QMemberClasses.*;
import static com.twg0.myacademy.domain.exam.entity.QExam.*;
import static com.twg0.myacademy.domain.member.entity.QMember.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.QClasses;
import com.twg0.myacademy.domain.member.entity.QMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomClassesRepositoryImpl implements CustomClassesRepository{
	private final JPAQueryFactory jpaQueryFactory;

}
