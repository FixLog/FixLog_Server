package com.example.FixLog.repository.bookmark;

import com.example.FixLog.domain.bookmark.Bookmark;
import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByFolderIdAndPostId(BookmarkFolder folderId, Post postId);

    @EntityGraph(attributePaths = {"postId.postLikes"})  // 좋아요 수 때문
    Page<Bookmark> findByFolderId(BookmarkFolder folder, Pageable pageable);
}
