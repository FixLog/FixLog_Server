package com.example.fixlog.service;

import com.example.fixlog.dto.follow.response.FollowResponseDto;
import com.example.fixlog.dto.follow.response.FollowerListResponseDto;
import com.example.fixlog.dto.follow.response.FollowingListResponseDto;
import com.example.fixlog.exception.CustomException;
import com.example.fixlog.exception.ErrorCode;
import com.example.fixlog.repository.MemberRepository;
import com.example.fixlog.repository.follow.FollowRepository;
import lombok.RequiredArgsConstructor;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.follow.Follow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    // 팔로우하기
    @Transactional
    public FollowResponseDto follow(String requesterEmail, Long targetMemberId){
        Member follower = memberRepository.findByEmail(requesterEmail)
<<<<<<< HEAD:src/main/java/com/example/fixlog/service/follow/FollowService.java
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_EMAIL_NOT_FOUND));
        Member following = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_ID_NOT_FOUND));
=======
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUNT));
        Member following = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));
>>>>>>> b32ddb2b758a53e321c9ae679c23589f56f3b63c:src/main/java/com/example/FixLog/service/FollowService.java

        // 자기 자신은 팔로우 불가
        if (follower.getUserId().equals(following.getUserId())) {
            throw new CustomException(ErrorCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        // 중복 팔로우 방지
        if (followRepository.existsByFollowerIdAndFollowingId(follower, following)) {
            throw new CustomException(ErrorCode.ALREADY_FOLLOWING);
        }

        Follow follow = new Follow(follower, following);
        Follow saved = followRepository.save(follow);

        return new FollowResponseDto(saved.getFollowId(), following.getUserId(), following.getNickname());
    }

    // 언팔로우하기
    @Transactional
    public void unfollow(String requesterEmail, Long targetMemberId) {
        Member follower = memberRepository.findByEmail(requesterEmail)
<<<<<<< HEAD:src/main/java/com/example/fixlog/service/follow/FollowService.java
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_EMAIL_NOT_FOUND));

        Member following = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_ID_NOT_FOUND));
=======
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUNT));

        Member following = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));
>>>>>>> b32ddb2b758a53e321c9ae679c23589f56f3b63c:src/main/java/com/example/FixLog/service/FollowService.java

        // 자기 자신은 팔로우 불가
        if (follower.getUserId().equals(following.getUserId())) {
            throw new CustomException(ErrorCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        Follow follow = followRepository.findByFollowerIdAndFollowingId(follower, following)
                .orElseThrow(() -> new CustomException(ErrorCode.SELF_UNFOLLOW_NOT_ALLOWED));

        followRepository.delete(follow);
    }

    // 나를 팔로우하는 목록 조회 (팔로워들)
    @Transactional(readOnly = true)
    public List<FollowerListResponseDto> getMyFollowers(String requesterEmail) {
        Member me = memberRepository.findByEmail(requesterEmail)
<<<<<<< HEAD:src/main/java/com/example/fixlog/service/follow/FollowService.java
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_EMAIL_NOT_FOUND));
=======
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUNT));
>>>>>>> b32ddb2b758a53e321c9ae679c23589f56f3b63c:src/main/java/com/example/FixLog/service/FollowService.java

        List<Follow> follows = followRepository.findByFollowingId(me);

        return follows.stream()
                .map(follow -> new FollowerListResponseDto(
                        follow.getFollowId(),
                        follow.getFollowerId().getUserId(),
                        follow.getFollowerId().getNickname()
                ))
                .toList();
    }

    // 내가 팔로우하는 목록 조회 (팔로잉들)
    @Transactional(readOnly = true)
    public List<FollowingListResponseDto> getMyFollowings(String requesterEmail) {
        Member me = memberRepository.findByEmail(requesterEmail)
<<<<<<< HEAD:src/main/java/com/example/fixlog/service/follow/FollowService.java
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_EMAIL_NOT_FOUND));
=======
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUNT));
>>>>>>> b32ddb2b758a53e321c9ae679c23589f56f3b63c:src/main/java/com/example/FixLog/service/FollowService.java

        List<Follow> follows = followRepository.findByFollowerId(me);

        return follows.stream()
                .map(follow -> new FollowingListResponseDto(
                        follow.getFollowId(),
                        follow.getFollowingId().getUserId(),
                        follow.getFollowingId().getNickname()
                ))
                .toList();
    }

}
