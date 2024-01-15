package com.twg0.myacademy.domain.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.myacademy.domain.academy.DTO.AcademyRequest;
import com.twg0.myacademy.domain.academy.service.AcademyService;
import com.twg0.myacademy.domain.classes.DTO.ClassesRequest;
import com.twg0.myacademy.domain.classes.service.ClassesService;
import com.twg0.myacademy.domain.member.DTO.MemberRequest;
import com.twg0.myacademy.domain.member.enums.Role;
import com.twg0.myacademy.domain.member.service.MemberService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service
@Transactional
@RequiredArgsConstructor
public class CSVReader {
	private final AcademyService academyService;
	private final ClassesService classesService;
	private final MemberService memberService;

	// @PostConstruct
	public void setUpData() {
		for (int i = 0; i < 3; i++) {
			File csv = null;
			if(i == 0) {
				csv = new File(
					"C:\\Users\\twg0\\Desktop\\Develop\\MyProject\\MyAcademy\\src\\main\\resources\\csvfile\\학원.CSV"
				);
			} else if(i == 1) {
				csv = new File(
					"C:\\Users\\twg0\\Desktop\\Develop\\MyProject\\MyAcademy\\src\\main\\resources\\csvfile\\반.CSV"
				);
			} else {
				csv = new File(
					"C:\\Users\\twg0\\Desktop\\Develop\\MyProject\\MyAcademy\\src\\main\\resources\\csvfile\\학생.csv"
				);
			}
			ArrayList<List<String>> csvList = new ArrayList<List<String>>();

			BufferedReader br = null;
			String line = "";

			try {
				br = new BufferedReader(new FileReader(csv));
				while ((line = br.readLine()) != null) {
					List<String> aLine = new ArrayList<>();
					String[] lineArr = line.split(",");
					aLine = List.of(lineArr);
					csvList.add(aLine);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if(i == 0) {
				for (List<String> strings : csvList) {
					if (strings.get(0).equals("이름"))
						continue;
					academyService.create(
						AcademyRequest.builder()
							.name(strings.get(0))
							.phoneNumber(strings.get(1))
							.address(strings.get(2))
							.academyUserId(strings.get(3))
							.password(strings.get(4))
							.studentNumber(0)
							.build()
					);
				}
			} else if(i == 1) {
				for (List<String> strings : csvList) {
					if (strings.get(0).equals("이름"))
						continue;
					classesService.create(
						ClassesRequest.builder()
							.className(strings.get(0) + strings.get(1))
							.subject(strings.get(1))
							.teacher(strings.get(2))
							.countOfStudent(0)
							.build(), strings.get(3)
					);
				}
			} else {
				for (List<String> strings : csvList) {
					if (strings.get(0).equals("이름"))
						continue;
					String[] split = strings.get(7).split("-");
					if(split[1].equals("2")) split[2] = "28";
					LocalDateTime birth = LocalDateTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
						Integer.parseInt(split[2]), 0, 0);
					// 이름, 반, 학원, id, pw, age, school, birth
					memberService.create(
						MemberRequest.builder()
							.username(strings.get(0))
							.userId(strings.get(3))
							.password(strings.get(4))
							.age(5)
							.school(strings.get(6))
							.birth(birth)
							.build(), strings.get(2), Role.MEMBER
					);
				}
			}
		}
	}
}
