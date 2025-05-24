package com.example.fixlog.repository.bookmark;

import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.member.Member;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
    Page<BookmarkFolder> findAllByOwner(Member owner, Pageable pageable);
=======
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
    BookmarkFolder findByUserId(Member userId);
>>>>>>> b32ddb2b758a53e321c9ae679c23589f56f3b63c
}
