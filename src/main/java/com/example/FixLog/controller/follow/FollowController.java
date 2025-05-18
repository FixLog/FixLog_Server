package com.example.FixLog.controller.follow;

import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.follow.request.FollowRequestDto;
import com.example.FixLog.dto.follow.request.UnfollowRequestDto;
import com.example.FixLog.dto.follow.response.FollowResponseDto;
import com.example.FixLog.dto.follow.response.FollowerListResponseDto;
import com.example.FixLog.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // 팔로우하기
    @PostMapping
    public ResponseEntity<Response<FollowResponseDto>> follow(
            @RequestBody FollowRequestDto followRequestDto,
            @RequestParam String requesterEmail // jwt 구현 전까지 임시 사용 -> 이후 AuthenticationPrincipal 사용 예정
            ){
        FollowResponseDto result = followService.follow(requesterEmail, followRequestDto.getTargetMemberId());
        return ResponseEntity.ok(Response.success("팔로우 완료", result));
    }

    // 언팔로우하기
    @PostMapping("/unfollow")
    public ResponseEntity<Response<Void>> unfollow(
            @RequestBody UnfollowRequestDto requestDto,
            @RequestParam String requesterEmail) {

        followService.unfollow(requesterEmail, requestDto.getTargetMemberId());
        return ResponseEntity.ok(Response.success("언팔로우 완료", null));
    }

    // 나를 팔로우하는 목록 조회
    @GetMapping("/followers")
    public ResponseEntity<Response<List<FollowerListResponseDto>>> getMyFollowers(
            @RequestParam String requesterEmail) {

        List<FollowerListResponseDto> followers = followService.getMyFollowers(requesterEmail);
        return ResponseEntity.ok(Response.success("나를 팔로우하는 목록 조회 성공", followers));
    }

}
