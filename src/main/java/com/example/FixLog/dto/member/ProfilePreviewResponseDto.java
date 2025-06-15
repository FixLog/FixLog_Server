package com.example.FixLog.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfilePreviewResponseDto {
    private String nickname;
    private String profileImageUrl;
}
