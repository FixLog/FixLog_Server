package com.example.FixLog.dto.member;

import com.example.FixLog.domain.member.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponseDto {
    private String email;
    private String nickname;
    private String profileImageUrl;
    private String bio;
    private SocialType socialType;
}
