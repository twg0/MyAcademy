package com.twg0.myacademy.domain.classes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.classes.entity.MemberClasses;

@Repository
public interface MemberClassesRepository extends JpaRepository<MemberClasses, Long> {
}
