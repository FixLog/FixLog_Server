package com.example.FixLog.domain.bookmark.repository;

import com.example.FixLog.domain.bookmark.domain.BookmarkFolder;
import com.example.FixLog.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
    Page<BookmarkFolder> findAllByUserId(Member userId, Pageable pageable);
    BookmarkFolder findByUserId(Member userId);
    Optional<BookmarkFolder> findFirstByUserId(Member userId); // 첫 번째 폴더만 가져올 때 Optional
}
