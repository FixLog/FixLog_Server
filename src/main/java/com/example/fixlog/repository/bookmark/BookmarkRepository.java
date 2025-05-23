package com.example.fixlog.repository.bookmark;

import com.example.fixlog.domain.bookmark.Bookmark;
import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByFolderAndPost(BookmarkFolder folderId, Post postId);
}
