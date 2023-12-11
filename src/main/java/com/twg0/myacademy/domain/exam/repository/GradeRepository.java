package com.twg0.myacademy.domain.exam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.exam.entity.Exam;
import com.twg0.myacademy.domain.exam.entity.Grade;
import com.twg0.myacademy.domain.member.entity.Member;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

	Optional<Grade> findByMemberExam(String memberExam);

	List<Grade> findAllByMember(Member member);

	List<Grade> findAllByExam(Exam exam);

	void deleteByMemberExam(String memberExam);
}
