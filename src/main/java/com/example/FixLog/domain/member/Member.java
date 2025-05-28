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

    // 프로필 사진 url, 지금은 nullable 이지만 나중에 기본값 설정
    @Column
    private String profileImageUrl;

    @Column(length = 200)
    private String bio;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    // 북마크 폴더
    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private BookmarkFolder bookmarkFolderId;
    // 우선은 계정 당 폴더 하나만 있는 걸로 생성
    // @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<BookmarkFolder> bookmarkFolders = new ArrayList<>();

    // Member 객체를 정적 팩토리 방식으로 회원가입 시에 생성하는 메서드
    // Member 객체를 정적 팩토리 방식으로 생성하는 메서드
    // Creates a Member object using a static factory method
    public static Member of(String email, String password, String nickname, SocialType socialType) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickname = nickname;
        member.socialType = socialType;
        member.isDeleted = false;
        member.profileImageUrl = "https://dummyimage.com/200x200/cccccc/ffffff&text=Profile"; // 기본 프로필 이미지(임시)
        return member;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // 기본 권한
    }

    @Override
    public String getUsername() {
        return this.email; // 로그인 시 사용할 사용자 식별자
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (true = 사용 가능)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (true = 잠금 아님)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted; // 탈퇴 여부 기반 활성 상태
    }
}