package com.example.FixLog.repository.follow;

import com.example.FixLog.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FixLog.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerIdAndFollowingId(Member follower, Member following); // 중복 방지 - 같은 사람 다시 팔로우
    Optional<Follow> findByFollowerIdAndFollowingId(Member follower, Member following); // 팔로우 관계 조회
    List<Follow> findByFollowingId(Member following); // 나를 팔로우하는 사용자 조회 (팔로워들)
    List<Follow> findByFollowerId(Member follower); // 내가 팔로우하는 사용자 조회 (팔로잉들)
}
