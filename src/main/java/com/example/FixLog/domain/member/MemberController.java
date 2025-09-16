package com.example.FixLog.domain.member;

import com.example.FixLog.domain.member.domain.Member;
import com.example.FixLog.common.exception.Response;
import com.example.FixLog.domain.member.dto.WithdrawRequestDto;
import com.example.FixLog.domain.member.dto.MemberInfoResponseDto;
import com.example.FixLog.domain.member.dto.ProfilePreviewResponseDto;
import com.example.FixLog.domain.member.dto.SignupRequestDto;
import com.example.FixLog.domain.member.dto.DuplicateCheckResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<Response<MemberInfoResponseDto>> getMyInfo(@AuthenticationPrincipal Member member) {
        System.out.println("회원 정보 조회 요청: " + member.getEmail()); //추후에 log.info()로 바꿀 예정
        MemberInfoResponseDto responseDto = new MemberInfoResponseDto(
                member.getEmail(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getBio(),
                member.getSocialType()
        );
        System.out.println("회원 정보 조회 완료: " + member.getEmail());
        return ResponseEntity.ok(Response.success("회원 정보 조회 성공", responseDto));
    }

    @GetMapping("/profile-preview")
    public ResponseEntity<Response<ProfilePreviewResponseDto>> getProfilePreview() {
        Member member = memberService.getCurrentMemberInfo();
        ProfilePreviewResponseDto dto = new ProfilePreviewResponseDto(
                member.getNickname(),
                member.getProfileImageUrl()
        );
        return ResponseEntity.ok(Response.success("닉네임&프로필사진 조회 성공", dto));
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/me")
    public ResponseEntity<Response<Void>> withdraw(
            @AuthenticationPrincipal Member member,
            @RequestBody WithdrawRequestDto request
    ) {
        memberService.withdraw(member, request.getPassword());
        return ResponseEntity.ok(Response.success("회원 탈퇴 성공", null));
    }
}