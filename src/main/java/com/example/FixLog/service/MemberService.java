package com.example.FixLog.service;

import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.member.SocialType;
import com.example.FixLog.dto.member.SignupRequestDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.bookmark.BookmarkFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookmarkFolderRepository bookmarkFolderRepository;

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
        // 기본 프로필 이미지 URL 생성
        member.setProfileImageUrl("https://dummyimage.com/200x200/cccccc/ffffff&text=Profile");
        // 먼저 회원 정보 저장
        memberRepository.save(member);

        // 기본 폴더 생성
        BookmarkFolder newFolder = new BookmarkFolder(member);
        bookmarkFolderRepository.save(newFolder);

    }

    public boolean isEmailDuplicated(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public boolean isNicknameDuplicated(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }
  
    // 현재 로그인한 사용자 정보 member 객체로 반환
    public Member getCurrentMemberInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUND));
    }
  
    // 회원탈퇴
    public void withdraw(Member member) {
        member.setIsDeleted(true);
        memberRepository.save(member);
    }
}

