package com.example.FixLog.domain.post;

import com.example.FixLog.domain.bookmark.Bookmark;
import com.example.FixLog.domain.like.PostLike;
import com.example.FixLog.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member userId;

    @Column(length = 100, nullable = false)
    private String postTitle;

    @Column(columnDefinition = "TEXT")
    private String coverImage;

    @Lob // 텍스트가 길어질 수 있는 필드에 사용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String problem;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String errorMessage;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String environment;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String reproduceCode;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String solutionCode;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String causeAnalysis;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String referenceLink;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String extraContent;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime editedAt;

    // 북마크와의 관계
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    // 태그와의 관계
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    // 좋아요와의 관계
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    public void clearTags(){
        postTags.clear();
    }

    public void changeTitle(String newTitle){
        this.postTitle = newTitle;
    }
    public void changeCoverImage(String newCoverImage){
        this.coverImage=newCoverImage;
    }
    public void changeProblem(String newProblem){
        this.problem = newProblem;
    }
    public void changeErrorMessage(String newErrorMessage){
        this.errorMessage = newErrorMessage;
    }
    public void changeEnvironment(String newEnvironment){
        this.environment = newEnvironment;
    }
    public void changeReproduceCode(String newReproduceCode){
        this.reproduceCode = newReproduceCode;
    }
    public void changeSolutionCode(String newSolutionCode){
        this.solutionCode = newSolutionCode;
    }
    public void changeCauseAnalysis(String newCauseAnalysis){
        this.causeAnalysis = newCauseAnalysis;
    }
    public void changeReferenceLink(String newReferenceLink){
        this.referenceLink = newReferenceLink;
    }
    public void changeExtraContent(String newExtraContent){
        this.extraContent = newExtraContent;
    }
    public void updateEditedAt(LocalDateTime newLocalDateTime){
        this.editedAt = newLocalDateTime;
    }
}