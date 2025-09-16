package com.example.FixLog.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfilePreviewResponseDto {
    private String nickname;
    private String profileImageUrl;
}
