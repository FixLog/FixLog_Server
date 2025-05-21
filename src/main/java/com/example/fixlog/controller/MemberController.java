package com.example.fixlog.controller;

import com.example.fixlog.dto.Response;
import com.example.fixlog.dto.memberdto.SignupRequestDto;
import com.example.fixlog.dto.memberdto.DuplicateCheckResponseDto;
import com.example.fixlog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Response<String>> signup(@RequestBody SignupRequestDto request) {
        memberService.signup(request);
        return ResponseEntity.ok(Response.success("회원가입이 완료되었습니다.", null));
    }

    @GetMapping("/check-email")
    public ResponseEntity<Response<DuplicateCheckResponseDto>> checkEmail(@RequestParam String email) {
        boolean exists = memberService.isEmailDuplicated(email);
        String msg = exists ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.";
        return ResponseEntity.ok(Response.success(msg, new DuplicateCheckResponseDto(exists)));
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<Response<DuplicateCheckResponseDto>> checkNickname(@RequestParam String nickname) {
        boolean exists = memberService.isNicknameDuplicated(nickname);
        String msg = exists ? "이미 사용 중인 닉네임입니다." : "사용 가능한 닉네임입니다.";
        return ResponseEntity.ok(Response.success(msg, new DuplicateCheckResponseDto(exists)));
    }
}