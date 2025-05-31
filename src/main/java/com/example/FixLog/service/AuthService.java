package com.example.FixLog.service;

import com.example.FixLog.domain.member.Member;
import com.example.FixLog.dto.member.LoginRequestDto;
import com.example.FixLog.dto.member.LoginResponseDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDto login(LoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NICKNAME_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtUtil.createToken(member.getUserId(), member.getEmail());

        // 로그인 응답 시에도 null-safe하게 처리
        String profileUrl = member.getProfileImageUrl() != null
                ? member.getProfileImageUrl()
                : "https://dummyimage.com/200x200/cccccc/ffffff&text=Profile";

        return new LoginResponseDto(
                member.getUserId(),
                token,
                member.getNickname(),
                member.getProfileImageUrl() != null
                        ? member.getProfileImageUrl()
                        : "https://your-cdn.com/images/default-profile.png");
    }
}
