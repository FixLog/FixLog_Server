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

        System.out.println("로그인 요청: " + requestDto.getEmail());

        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NICKNAME_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtUtil.createToken(member.getUserId(), member.getEmail());

        System.out.println("로그인 성공: " + member.getEmail() + " (nickname: " + member.getNickname() + ")");

        // 응답에서 null-safe하게 기본 이미지 처리 포함
        return LoginResponseDto.from(member, token);
    }
}