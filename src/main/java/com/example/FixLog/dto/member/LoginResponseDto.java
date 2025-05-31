package com.example.FixLog.dto.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String accessToken;
    private String nickname;
    private String profileImageUrl;
}
