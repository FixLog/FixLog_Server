package com.example.FixLog.service.follow;

import com.example.FixLog.dto.follow.response.FollowResponseDto;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.follow.FollowRepository;
import lombok.RequiredArgsConstructor;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.follow.Follow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    // 팔로우하기
    @Transactional
    public FollowResponseDto follow(String requesterEmail, Long targetMemberId){
        Member follower = memberRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없음"));
        Member following = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 대상 없음"));

        // 자기 자신은 팔로우 불가
        if (follower.getId().equals(following.getId())) {
            throw new IllegalArgumentException("자기 자신은 팔로우할 수 없음");
        }

        // 중복 팔로우 방지
        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new IllegalStateException("이미 팔로우 중");
        }

        Follow follow = new Follow(follower, following);
        Follow saved = followRepository.save(follow);

        return new FollowResponseDto(saved.getId(), following.getId(), following.getNickname());
    }

    // 언팔로우하기
    @Transactional
    public void unfollow(String requesterEmail, Long targetMemberId) {
        Member follower = memberRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new IllegalArgumentException("요청자 회원 없음"));

        Member following = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new IllegalArgumentException("언팔로우 대상 없음"));

        // 자기 자신은 팔로우 불가
        if (follower.getId().equals(following.getId())) {
            throw new IllegalArgumentException("자기 자신은 팔로우할 수 없음");
        }

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않음"));

        followRepository.delete(follow);
    }

}
