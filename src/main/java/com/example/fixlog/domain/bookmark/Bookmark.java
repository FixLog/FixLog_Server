package com.example.fixlog.domain.bookmark;

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
    @Column(name="bookmark_id")
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private BookmarkFolder folderId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member;  // 삭제해야함

    private boolean isMarked;

    public Bookmark(BookmarkFolder folderId, Post postId){
        this.folderId = folderId;
        this.postId = postId;
    }

    public void ToggleBookmark(boolean state){
        this.isMarked = state;
    }
}
