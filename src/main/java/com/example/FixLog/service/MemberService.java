package com.example.FixLog.service;

import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.member.SocialType;
import com.example.FixLog.dto.member.LoginResponseDto;
import com.example.FixLog.dto.member.MemberInfoResponseDto;
import com.example.FixLog.dto.member.SignupRequestDto;
import com.example.FixLog.dto.member.edit.EditPasswordRequestDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.bookmark.BookmarkFolderRepository;
import com.example.FixLog.util.DefaultImage;
import com.example.FixLog.util.DefaultText;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookmarkFolderRepository bookmarkFolderRepository;

    /**
     * 회원가입 로직
     */
    @Transactional
    public void signup(SignupRequestDto request) {
        validateEmail(request.getEmail());
        validateNickname(request.getNickname());

        Member member = Member.of(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                SocialType.EMAIL
        );

        memberRepository.save(member);
        bookmarkFolderRepository.save(new BookmarkFolder(member));
    }

    /**
     * 현재 로그인한 사용자 조회
     */
    @Transactional(readOnly = true)
    public Member getCurrentMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUND));
    }

    /**
     * 닉네임 수정
     */
    @Transactional
    public void editNickname(Member member, String newNickname) {
        validateNickname(newNickname);
        member.updateNickname(newNickname);
        memberRepository.save(member);
    }

    /**
     * 비밀번호 수정
     */
    @Transactional
    public void editPassword(Member member, EditPasswordRequestDto dto) {
        validatePasswordChange(dto.getCurrentPassword(), dto.getNewPassword(), member.getPassword());
        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        memberRepository.save(member);
    }

    /**
     * 프로필 이미지 수정
     */
    @Transactional
    public void editProfileImage(Member member, String newProfileImageUrl) {
        String finalImage = Optional.ofNullable(newProfileImageUrl).orElse(DefaultImage.PROFILE);
        member.updateProfileImage(finalImage);
        memberRepository.save(member);
    }

    /**
     * 소개글 수정
     */
    @Transactional
    public void editBio(Member member, String newBio) {
        String finalBio = Optional.ofNullable(newBio).orElse(DefaultText.BIO);
        member.updateBio(finalBio);
        memberRepository.save(member);
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void withdraw(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        member.markAsDeleted();
        memberRepository.save(member);
    }

    /**
     * 내 정보 조회 응답 DTO 생성
     */
    @Transactional(readOnly = true)
    public MemberInfoResponseDto getMyInfo() {
        Member member = getCurrentMemberInfo();

        String profileImage = Optional.ofNullable(member.getProfileImageUrl()).orElse(DefaultImage.PROFILE);
        String bio = Optional.ofNullable(member.getBio()).orElse(DefaultText.BIO);

        return new MemberInfoResponseDto(
                member.getEmail(),
                member.getNickname(),
                profileImage,
                bio,
                member.getSocialType()
        );
    }

    /**
     * 로그인 응답 DTO 생성
     */
    public LoginResponseDto getLoginResponse(Member member, String accessToken) {
        String profileImage = Optional.ofNullable(member.getProfileImageUrl()).orElse(DefaultImage.PROFILE);

        return new LoginResponseDto(
                member.getUserId(),
                accessToken,
                member.getNickname(),
                profileImage
        );
    }

    // ========================== 검증 메서드 ==========================

    public void validateEmail(String email) {
        if (isEmailDuplicated(email)) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }
    }

    public void validateNickname(String nickname) {
        if (isNicknameDuplicated(nickname)) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }
    }

    public boolean isEmailDuplicated(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public boolean isNicknameDuplicated(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }

    public void validatePasswordChange(String currentPassword, String newPassword, String encodedPassword) {
        if (!passwordEncoder.matches(currentPassword, encodedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        if (passwordEncoder.matches(newPassword, encodedPassword)) {
            throw new CustomException(ErrorCode.SAME_AS_OLD_PASSWORD);
        }
    }
}