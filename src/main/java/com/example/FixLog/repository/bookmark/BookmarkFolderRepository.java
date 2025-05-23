package com.example.fixlog.repository.bookmark;

import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
    BookmarkFolder findByOwner(Member userId);
}
