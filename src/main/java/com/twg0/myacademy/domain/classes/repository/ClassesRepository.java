package com.twg0.myacademy.domain.classes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.classes.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {

	Optional<Classes> findByClassName(String className);

	List<Classes> findAllByClassName(String className);

	void deleteByClassName(String className);

	boolean existsByClassName(String className);
}
