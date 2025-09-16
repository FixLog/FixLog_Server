package com.example.FixLog.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresignResponseDto {
    private final String uploadUrl; // PUT 전용 Presigned URL
    private final String fileUrl;   // public하게 접근 가능한 URL
}
