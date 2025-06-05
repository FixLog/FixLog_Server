package com.example.FixLog.dto.member;

import com.example.FixLog.domain.member.Member;
import com.example.FixLog.util.DefaultImage;
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

    // 정적 팩토리 메서드
    public static LoginResponseDto from(Member member, String accessToken) {
        return new LoginResponseDto(
                member.getUserId(),
                accessToken,
                member.getNickname(),
                member.getProfileImageUrl() != null
                        ? member.getProfileImageUrl()
                        : DefaultImage.PROFILE
        );
    }
}
