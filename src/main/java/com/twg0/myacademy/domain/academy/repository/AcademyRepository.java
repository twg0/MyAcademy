package com.twg0.myacademy.domain.academy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.academy.entity.Academy;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Long> {

	Optional<Academy> findByAcademyId(final String academyId);

	void deleteByAcademyId(final String academyId);

	boolean existsByAcademyId(final String academyId);
}
