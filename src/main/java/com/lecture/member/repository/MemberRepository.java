package com.lecture.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lecture.member.domain.Email;
import com.lecture.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(Email email);
}
