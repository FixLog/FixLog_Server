package com.example.FixLog.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    //비밀번호 재확인은 프론트단에서 확인
    private String password;
    private String nickname;
}