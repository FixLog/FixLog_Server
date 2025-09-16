package com.example.FixLog.domain.follow;

import com.example.FixLog.common.exception.Response;
import com.example.FixLog.domain.follow.dto.request.FollowRequestDto;
import com.example.FixLog.domain.follow.dto.request.UnfollowRequestDto;
import com.example.FixLog.domain.follow.dto.response.FollowResponseDto;
import com.example.FixLog.domain.follow.dto.response.FollowerListResponseDto;
import com.example.FixLog.domain.follow.dto.response.FollowingListResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // 팔로우하기
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<Response<FollowResponseDto>> follow(
            @RequestBody FollowRequestDto followRequestDto,
            @AuthenticationPrincipal UserDetails userDetails  // jwt 구현 전까지 임시 사용 -> 이후 AuthenticationPrincipal 사용 예정
    ){
        String requesterEmail = userDetails.getUsername();
        FollowResponseDto result = followService.follow(requesterEmail, followRequestDto.getTargetMemberId());
        return ResponseEntity.ok(Response.success("팔로우 완료", result));
    }

    // 언팔로우하기
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/unfollow")
    public ResponseEntity<Response<Void>> unfollow(
            @RequestBody UnfollowRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String requesterEmail = userDetails.getUsername();
        followService.unfollow(requesterEmail, requestDto.getTargetMemberId());
        return ResponseEntity.ok(Response.success("언팔로우 완료", null));
    }

    // 나를 팔로우하는 목록 조회
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/followers")
    public ResponseEntity<Response<List<FollowerListResponseDto>>> getMyFollowers(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String requesterEmail = userDetails.getUsername();
        List<FollowerListResponseDto> followers = followService.getMyFollowers(requesterEmail);
        return ResponseEntity.ok(Response.success("나를 팔로우하는 목록 조회 성공", followers));
    }

    // 내가 팔로우하는 목록 조회
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/followings")
    public ResponseEntity<Response<List<FollowingListResponseDto>>> getMyFollowings(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String requesterEmail = userDetails.getUsername();
        List<FollowingListResponseDto> followings = followService.getMyFollowings(requesterEmail);
        return ResponseEntity.ok(Response.success("내가 팔로우 중인 목록 조회 성공", followings));
    }
}
