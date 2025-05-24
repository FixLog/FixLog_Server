package com.example.fixlog.repository.bookmark;

import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
    Page<BookmarkFolder> findAllByOwner(Member owner, Pageable pageable);
    BookmarkFolder findByUserId(Member userId);
}
