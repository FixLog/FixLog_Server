package com.example.fixlog.domain.bookmark;

import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.post.Post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookmarkId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folderId")
    private BookmarkFolder folder;

    private boolean isMarked;

    public Bookmark(Member userId, Post postId){
        this.member = userId;
        this.post = postId;
    }

    public void ToggleBookmark(boolean state){
        this.isMarked = state;
    }
}
