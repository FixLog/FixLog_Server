package com.example.fixlog.domain.fork;

import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.post.Post;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fork_post_id", nullable = false)
    private Post forkedPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_post_id", nullable = false)
    private Post originPost;
}
