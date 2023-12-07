package com.twg0.myacademy.domain.classes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.member.entity.Member;

@Repository
public interface MemberClassesRepository extends JpaRepository<MemberClasses, Long> {

	List<MemberClasses> findByMember(Member member);

	List<MemberClasses> findByClasses(Classes classes);
}
