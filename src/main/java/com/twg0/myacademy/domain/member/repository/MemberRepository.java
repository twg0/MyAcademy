package com.twg0.myacademy.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twg0.myacademy.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByUserId(String userId);


}
