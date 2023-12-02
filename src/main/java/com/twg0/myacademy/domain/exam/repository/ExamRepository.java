package com.twg0.myacademy.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.exam.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
}
