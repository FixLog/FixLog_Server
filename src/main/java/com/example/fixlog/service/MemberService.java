package com.example.fixlog.service;

import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.member.SocialType;
import com.example.fixlog.dto.memberdto.SignupRequestDto;
import com.example.fixlog.exception.CustomException;
import com.example.fixlog.exception.ErrorCode;
import com.example.fixlog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto request) {
        // 이메일 중복 검사
        if (isEmailDuplicated(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }

        // 닉네임 중복 검사
        if (isNicknameDuplicated(request.getNickname())) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }

        // 문제 없으면 저장
        Member member = Member.of(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                SocialType.EMAIL
        );

        memberRepository.save(member);
    }

    public boolean isEmailDuplicated(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public boolean isNicknameDuplicated(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }
}