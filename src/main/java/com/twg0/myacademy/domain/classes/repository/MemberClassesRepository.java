package com.twg0.myacademy.domain.classes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.classes.entity.Classes;
import com.twg0.myacademy.domain.classes.entity.MemberClasses;
import com.twg0.myacademy.domain.classes.entity.MemberClassesID;
import com.twg0.myacademy.domain.member.entity.Member;

@Repository
public interface MemberClassesRepository extends JpaRepository<MemberClasses, MemberClassesID> {

	Optional<MemberClasses> findByMemberAndClasses(Member member, Classes classes);
	List<MemberClasses> findByMember(Member member);

	List<MemberClasses> findByClasses(Classes classes);

	void deleteByMemberAndClasses(Member member, Classes classes);
}
