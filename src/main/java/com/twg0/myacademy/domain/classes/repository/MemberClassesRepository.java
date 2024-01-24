package com.twg0.myacademy.domain.classes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twg0.myacademy.domain.classes.entity.MemberClasses;

public interface MemberClassesRepository extends JpaRepository<MemberClasses, Long> {
}
