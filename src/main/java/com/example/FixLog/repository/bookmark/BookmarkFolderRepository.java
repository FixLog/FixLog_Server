package com.example.fixlog.repository.bookmark;

import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
    List<BookmarkFolder> findAllByOwner(Member owner);
}
