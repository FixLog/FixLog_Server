package com.example.FixLog.domain.member;

import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType = SocialType.EMAIL;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;

    // 프로필 사진 URL
    @Column
    private String profileImageUrl;

    @Column(length = 200)
    private String bio;

    // 게시글 연관관계
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    // 북마크 폴더 (계정당 1개)
    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private BookmarkFolder bookmarkFolder;

    // 정적 팩토리 메서드
    public static Member of(String email, String password, String nickname, SocialType socialType) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickname = nickname;
        member.socialType = socialType;
        member.isDeleted = false;
        member.profileImageUrl = null; // 기본 이미지는 응답 시 처리
        return member;
    }

    // -------------------- 도메인 메서드 --------------------

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateProfileImage(String url) {
        this.profileImageUrl = url;
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    // -------------------- Spring Security --------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // 기본 권한
    }

    @Override
    public String getUsername() {
        return this.email; // 로그인 시 사용할 식별자
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 안 됨
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금 아님
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 안 됨
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted; // 탈퇴 계정은 비활성화
    }
}