package com.example.fixlog.domain.post;

import com.example.fixlog.domain.bookmark.Bookmark;
import com.example.fixlog.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Member member;

    @Column(length = 100, nullable = false)
    private String postTitle;

    @Column(columnDefinition = "TEXT")
    private String coverImage;

    @Lob // 텍스트가 길어질 수 있는 필드에 사용
    @Column(nullable = false)
    private String problem;

    @Lob
    @Column(nullable = false)
    private String errorMessage;

    @Lob
    @Column(nullable = false)
    private String environment;

    @Lob
    @Column(nullable = false)
    private String reproduceCode;

    @Lob
    @Column(nullable = false)
    private String solutionCode;

    @Lob
    private String causeAnalysis;

    @Lob
    private String referenceLink;

    @Lob
    private String extraContent;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime editedAt;

//    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private CoverImage coverImage;

    // 북마크와의 관계
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    public Post(Member userId, String postTitle, String coverImage, String problem, String errorMessage,
                String environment, String reproduceCode, String solutionCode, String causeAnalysis,
                String referenceLink, String extraContent, LocalDateTime createdAt, LocalDateTime editedAt){
        this.member = userId;
        this.postTitle = postTitle;
        this.coverImage = coverImage;
        this.problem = problem;
        this.errorMessage = errorMessage;
        this.environment = environment;
        this.reproduceCode = reproduceCode;
        this.solutionCode = solutionCode;
        this.causeAnalysis = causeAnalysis;
        this.referenceLink = referenceLink;
        this.extraContent = extraContent;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        // 게시글 이미지
        // 게시글 태그
    }
}