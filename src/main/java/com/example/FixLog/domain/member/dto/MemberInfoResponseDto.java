package com.example.FixLog.domain.member.dto;

import com.example.FixLog.domain.member.domain.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 서비스단에서 최대한 처리하도록 수정
@Getter
@AllArgsConstructor
public class MemberInfoResponseDto {
    private String email;
    private String nickname;
    private String profileImageUrl;
    private String bio;
    private SocialType socialType;
}
