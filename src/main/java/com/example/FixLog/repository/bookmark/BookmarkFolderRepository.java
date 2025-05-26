package com.example.FixLog.repository.bookmark;

import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
    Page<BookmarkFolder> findAllByUserId(Member userId, Pageable pageable);
    BookmarkFolder findByUserId(Member userId);
}
