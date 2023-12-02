package com.twg0.myacademy.domain.classes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.classes.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
}
