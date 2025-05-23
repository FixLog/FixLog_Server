package com.example.fixlog.domain.member;

import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    // 북마크 폴더
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private BookmarkFolder bookmarkFolder;
    // 우선은 계정 당 폴더 하나만 있는 걸로 생성
    // @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
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
        return member;
    }
}