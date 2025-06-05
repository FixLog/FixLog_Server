package com.example.FixLog.dto.member;

import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.member.SocialType;
import com.example.FixLog.util.DefaultImage;
import com.example.FixLog.util.DefaultText;
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

    public static MemberInfoResponseDto from(Member member) {
        return new MemberInfoResponseDto(
                member.getEmail(),
                member.getNickname(),
                member.getProfileImageUrl() != null
                        ? member.getProfileImageUrl()
                        : DefaultImage.PROFILE,
                member.getBio() != null
                        ? member.getBio()
                        : DefaultText.BIO,
                member.getSocialType()
        );
    }
}
