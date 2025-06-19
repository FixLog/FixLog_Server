package com.example.FixLog.controller;

import com.example.FixLog.dto.PresignResponseDto;
import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.member.edit.EditNicknameRequestDto;
import com.example.FixLog.dto.member.edit.EditPasswordRequestDto;
import com.example.FixLog.dto.member.edit.EditBioRequestDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.service.S3Service;
import com.example.FixLog.service.MemberService;
import com.example.FixLog.domain.member.Member;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageMemberController {

    private final MemberService memberService;
    private final S3Service s3Service;

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/members/nickname")
    public ResponseEntity<Response<String>> editNickname(
            @RequestBody @Valid EditNicknameRequestDto requestDto
    ) {
        Member member = memberService.getCurrentMemberInfo();
        memberService.editNickname(member, requestDto.getNickname());
        return ResponseEntity.ok(Response.success("닉네임 수정 성공", "SUCCESS"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/members/password")
    public ResponseEntity<Response<String>> editPassword(
            @RequestBody @Valid EditPasswordRequestDto requestDto
    ) {
        Member member = memberService.getCurrentMemberInfo();
        memberService.editPassword(member, requestDto);
        return ResponseEntity.ok(Response.success("비밀번호 변경 성공", "SUCCESS"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/members/profile-image/presign")
    public ResponseEntity<Response<PresignResponseDto>> presignProfileImage(
            @AuthenticationPrincipal Member member,
            @RequestParam String filename
    ) {
        if (member == null) throw new CustomException(ErrorCode.UNAUTHORIZED);

        String key = s3Service.generateKey("profile", filename);
        String uploadUrl = s3Service.generatePresignedUrl("profile", filename, 15);
        String fileUrl = s3Service.getObjectUrl(key);

        PresignResponseDto dto = new PresignResponseDto(uploadUrl, fileUrl);
        return ResponseEntity.ok(Response.success("Presigned URL 발급 성공", dto));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/members/profile-image")
    public ResponseEntity<Response<String>> updateProfileImageUrl(
            @AuthenticationPrincipal Member member,
            @RequestBody Map<String, String> body
    ) {
        String imageUrl = body.get("imageUrl");
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        memberService.editProfileImage(member, imageUrl);
        return ResponseEntity.ok(Response.success("프로필 이미지 저장 성공", "SUCCESS"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/members/bio")
    public ResponseEntity<Response<String>> editBio(
            @RequestBody @Valid EditBioRequestDto requestDto
    ) {
        Member member = memberService.getCurrentMemberInfo();
        memberService.editBio(member, requestDto.getBio());
        return ResponseEntity.ok(Response.success("소개글 수정 성공", "SUCCESS"));
    }
}