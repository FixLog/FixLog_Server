package com.example.fixLog.domain.fork;

import com.example.fixLog.domain.member.Member;
import com.example.fixLog.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forkId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private Post originalPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forkPostId", nullable = false)
    private Post forkedPost;
}
