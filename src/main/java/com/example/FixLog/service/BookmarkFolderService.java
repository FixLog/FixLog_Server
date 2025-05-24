package com.example.fixlog.service;

import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.dto.bookmark.response.BookmarkFolderCreateResponse;
import com.example.fixlog.exception.CustomException;
import com.example.fixlog.exception.ErrorCode;
import com.example.fixlog.repository.MemberRepository;
import com.example.fixlog.repository.bookmark.BookmarkFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkFolderService {

    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final MemberRepository memberRepository;

    // 북마크 폴더 생성
    public BookmarkFolderCreateResponse createFolder(String folderName, String requesterEmail) {
        Member member = memberRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBEREMAIL_NOT_FOUND));

        BookmarkFolder folder = new BookmarkFolder(folderName, member);
        BookmarkFolder saved = bookmarkFolderRepository.save(folder);

        return new BookmarkFolderCreateResponse(saved.getId(), saved.getName()); // 폴더 ID, 폴더 이름 리턴
    }

}
