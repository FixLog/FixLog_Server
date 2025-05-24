package com.example.fixlog.domain.like;

import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.post.Post;
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
    @Column(name = "likeId", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    private boolean isLiked;

    public PostLike(Member userId, Post postId){
        this.member = userId;
        this.post = postId;
        this.isLiked = true; // 객체 생성 시 true
    }

    public void ToggleLike(boolean state){
        this.isLiked = state;
    }
}
