package com.example.FixLog.domain.member;

import com.example.FixLog.domain.member.domain.Member;
import com.example.FixLog.domain.member.dto.LoginRequestDto;
import com.example.FixLog.domain.member.dto.LoginResponseDto;
import com.example.FixLog.common.exception.CustomException;
import com.example.FixLog.common.response.ErrorStatus;
import com.example.FixLog.domain.member.repository.MemberRepository;
import com.example.FixLog.common.util.JwtUtil;
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
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NICKNAME_NOT_FOUND));

        if (member.getIsDeleted()) {
            throw new CustomException(ErrorStatus.USER_DELETED);
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorStatus.INVALID_PASSWORD);
        }

        String token = jwtUtil.createToken(member.getUserId(), member.getEmail());

        System.out.println("로그인 성공: " + member.getEmail() + " (nickname: " + member.getNickname() + ")");

        // 응답에서 null-safe하게 기본 이미지 처리 포함
        return LoginResponseDto.from(member, token);
    }
}