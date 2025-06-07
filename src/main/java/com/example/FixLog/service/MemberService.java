package com.example.FixLog.service;

import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.member.SocialType;
import com.example.FixLog.dto.member.SignupRequestDto;
import com.example.FixLog.dto.member.edit.EditPasswordRequestDto;
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

        // 회원 객체 생성 (profileImageUrl = null)
        Member member = Member.of(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                SocialType.EMAIL
        );

        // 회원 저장
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
    public void withdraw(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        member.setIsDeleted(true);
        memberRepository.save(member);
    }


    public void editNickname(Member member, String newNickname) {
        if (isNicknameDuplicated(newNickname)) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }
        member.setNickname(newNickname);
        memberRepository.save(member);
    }

    public void editPassword(Member member, EditPasswordRequestDto dto) {
        String currentPassword = dto.getCurrentPassword();
        String newPassword = dto.getNewPassword();

        // 1. 현재 비밀번호 일치 확인
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD); // 기존 비밀번호 불일치
        }

        // 2. 새 비밀번호가 기존과 동일한 경우
        if (passwordEncoder.matches(newPassword, member.getPassword())) {
            throw new CustomException(ErrorCode.SAME_AS_OLD_PASSWORD); // 동일한 비밀번호
        }

        // 3. 새 비밀번호로 변경
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    public void editProfileImage(Member member, String newProfileImageUrl) {
        member.setProfileImageUrl(newProfileImageUrl);
        memberRepository.save(member);
    }

    public void editBio(Member member, String newBio) {
        member.setBio(newBio);
        memberRepository.save(member);
    }

    private void validateNickname(String nickname) {
        if (isNicknameDuplicated(nickname)) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }
    }
}