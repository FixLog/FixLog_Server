package com.example.FixLog.repository.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fixlog.domain.follow.Follow;
import com.example.fixlog.domain.member.Member;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(Member follower, Member following); // 중복 방지 - 같은 사람 다시 팔로우
    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following); // 팔로우 관계 조회
}
