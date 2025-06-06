package com.example.FixLog.controller;

import com.example.FixLog.domain.member.Member;
import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.member.MemberInfoResponseDto;
import com.example.FixLog.dto.member.SignupRequestDto;
import com.example.FixLog.dto.member.DuplicateCheckResponseDto;
import com.example.FixLog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Response<String>> signup(@RequestBody SignupRequestDto request) {
        memberService.signup(request);
        return ResponseEntity.ok(Response.success("회원가입 성공", null));
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

    @GetMapping("/me")
    public ResponseEntity<Response<MemberInfoResponseDto>> getMyInfo(@AuthenticationPrincipal Member member) {
        MemberInfoResponseDto responseDto = new MemberInfoResponseDto(
                member.getEmail(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getBio(),
                member.getSocialType()
        );
        return ResponseEntity.ok(Response.success("회원 정보 조회 성공", responseDto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Response<Void>> withdraw(@AuthenticationPrincipal Member member) {
        memberService.withdraw(member);
        return ResponseEntity.ok(Response.success("회원 탈퇴 성공", null));
    }
}