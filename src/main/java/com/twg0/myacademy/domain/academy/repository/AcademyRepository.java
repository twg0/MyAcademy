package com.twg0.myacademy.domain.academy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.academy.entity.Academy;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Long> {

	Optional<Academy> findByAcademyUserId(final String academyUserId);

	void deleteByAcademyUserId(final String academyUserId);

	boolean existsByAcademyUserId(final String academyUserId);
}
