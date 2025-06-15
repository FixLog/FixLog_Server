package com.example.FixLog.dto.member.edit;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditProfileImageRequestDto {

    @NotBlank(message = "프로필 이미지 URL은 필수입니다.")
    private String profileImageUrl;
}
