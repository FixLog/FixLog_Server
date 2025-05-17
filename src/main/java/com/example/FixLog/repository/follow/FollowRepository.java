package com.example.FixLog.repository.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fixlog.domain.follow.Follow;
import com.example.fixlog.domain.member.Member;
import org.springframework.stereotype.Repository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(Member follower, Member following);
}
