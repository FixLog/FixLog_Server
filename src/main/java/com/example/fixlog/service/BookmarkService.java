package com.example.fixlog.service;

import com.example.fixlog.domain.bookmark.Bookmark;
import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.exception.CustomException;
import com.example.fixlog.exception.ErrorCode;
import com.example.fixlog.repository.MemberRepository;
import com.example.fixlog.repository.bookmark.BookmarkFolderRepository;
import com.example.fixlog.repository.bookmark.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;

    // 북마크 폴더 이동
    public void moveBookmarkToFolder(Long bookmarkId, Long newFolderId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUNT));

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));

        // 폴더 주인만 이동 가능
        if (!bookmark.getFolderId().getUserId().equals(member)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        BookmarkFolder targetFolder = bookmarkFolderRepository.findById(newFolderId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOLDER_NOT_FOUND));

        bookmark.moveToFolder(targetFolder);
    }
}
