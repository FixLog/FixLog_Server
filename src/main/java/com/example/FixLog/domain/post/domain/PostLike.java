package com.example.FixLog.domain.post.domain;

import com.example.FixLog.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", nullable = false)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post postId;

    private boolean isLiked;

    public PostLike(Member userId, Post postId){
        this.userId = userId;
        this.postId = postId;
        this.isLiked = true; // 객체 생성 시 true
    }

    public void ToggleLike(boolean state){
        this.isLiked = state;
    }
}
