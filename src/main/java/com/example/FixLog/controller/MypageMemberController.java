package com.example.FixLog.controller;

import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.member.edit.EditNicknameRequestDto;
import com.example.FixLog.dto.member.edit.EditPasswordRequestDto;
import com.example.FixLog.dto.member.edit.EditProfileImageRequestDto;
import com.example.FixLog.dto.member.edit.EditBioRequestDto;
import com.example.FixLog.service.MemberService;
import com.example.FixLog.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageMemberController {

    private final MemberService memberService;

    @PatchMapping("/members/nickname")
    public ResponseEntity<Response<String>> editNickname(
            @RequestBody @Valid EditNicknameRequestDto requestDto
    ) {
        Member member = memberService.getCurrentMemberInfo();
        memberService.editNickname(member, requestDto.getNickname());
        return ResponseEntity.ok(Response.success("닉네임 수정 성공", "SUCCESS"));
    }

    @PatchMapping("/members/password")
    public ResponseEntity<Response<String>> editPassword(
            @RequestBody @Valid EditPasswordRequestDto requestDto
    ) {
        Member member = memberService.getCurrentMemberInfo();
        memberService.editPassword(member, requestDto);
        return ResponseEntity.ok(Response.success("비밀번호 변경 성공", "SUCCESS"));
    }

    @PatchMapping("/members/profile-image")
    public ResponseEntity<Response<String>> editProfileImage(
            @RequestBody @Valid EditProfileImageRequestDto requestDto
    ) {
        Member member = memberService.getCurrentMemberInfo();
        memberService.editProfileImage(member, requestDto.getProfileImageUrl());
        return ResponseEntity.ok(Response.success("프로필 이미지 수정 성공", "SUCCESS"));
    }

    @PatchMapping("/members/bio")
    public ResponseEntity<Response<String>> editBio(
            @RequestBody @Valid EditBioRequestDto requestDto
    ) {
        Member member = memberService.getCurrentMemberInfo();
        memberService.editBio(member, requestDto.getBio());
        return ResponseEntity.ok(Response.success("소개글 수정 성공", "SUCCESS"));
    }
}