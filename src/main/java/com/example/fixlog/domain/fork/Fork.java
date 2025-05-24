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
    @Column(name = "fork_id")
    private Long forkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post originalPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fork_post_id", nullable = false)
    private Post forkedPostId;
}
