package com.example.FixLog.controller.follow;

import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.follow.request.FollowRequestDto;
import com.example.FixLog.dto.follow.response.FollowResponseDto;
import com.example.FixLog.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping
    public ResponseEntity<Response<FollowResponseDto>> follow(
            @RequestBody FollowRequestDto followRequestDto,
            @RequestParam String requesterEmail // jwt 구현 전까지 임시 사용 -> 이후 AuthenticationPrincipal 사용 예정
            ){
        FollowResponseDto result = followService.follow(requesterEmail, followRequestDto.getTargetMemberId());
        return ResponseEntity.ok(Response.success("팔로우 완료", result));
    }
}
