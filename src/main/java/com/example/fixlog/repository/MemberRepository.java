package com.example.fixlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fixlog.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // 이메일로 회원 조회
}
