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
        System.out.println("회원가입 요청 - 이메일: " + request.getEmail());
        System.out.println("회원가입 요청 - 닉네임: " + request.getNickname());
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

        System.out.println("회원가입 완료: " + request.getEmail());
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
        System.out.println("닉네임 수정 요청: " + member.getNickname() + " → " + newNickname);
        validateNickname(newNickname);
        member.updateNickname(newNickname);
        memberRepository.save(member);
        System.out.println("닉네임 수정 완료: " + member.getNickname());
    }

    /**
     * 비밀번호 수정
     */
    @Transactional
    public void editPassword(Member member, EditPasswordRequestDto dto) {
        System.out.println("비밀번호 수정 요청: 이메일 - " + member.getEmail());
        validatePasswordChange(dto.getCurrentPassword(), dto.getNewPassword(), member.getPassword());
        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        memberRepository.save(member);
        System.out.println("비밀번호 수정 완료");
    }

    /**
     * 프로필 이미지 수정
     */
    @Transactional
    public void editProfileImage(Member member, String newProfileImageUrl) {
        System.out.println("프로필 이미지 수정 요청: " + member.getEmail());
        String finalImage = Optional.ofNullable(newProfileImageUrl).orElse(DefaultImage.PROFILE);
        member.updateProfileImage(finalImage);
        memberRepository.save(member);
        System.out.println("프로필 이미지 수정 완료");
    }


    /**
     * 소개글 수정
     */
    @Transactional
    public void editBio(Member member, String newBio) {
        System.out.println("소개글 수정 요청: " + member.getEmail());
        String finalBio = Optional.ofNullable(newBio).orElse(DefaultText.BIO);
        member.updateBio(finalBio);
        memberRepository.save(member);
        System.out.println("소개글 수정 완료");
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
        System.out.println("이메일 중복 검사 진행 중: " + email);
        if (isEmailDuplicated(email)) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }
    }

    public void validateNickname(String nickname) {
        System.out.println(" 닉네임 중복 검사 진행 중: " + nickname);
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