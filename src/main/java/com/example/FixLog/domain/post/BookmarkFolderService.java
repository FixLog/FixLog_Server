package com.example.FixLog.domain.post;

import com.example.FixLog.domain.bookmark.domain.BookmarkFolder;
import com.example.FixLog.domain.member.domain.Member;
import com.example.FixLog.domain.mypage.dto.PageResponseDto;
import com.example.FixLog.domain.bookmark.dto.response.BookmarkFolderCreateResponse;
import com.example.FixLog.domain.bookmark.dto.response.BookmarkFolderReadResponse;
import com.example.FixLog.common.exception.CustomException;
import com.example.FixLog.common.response.ErrorStatus;
import com.example.FixLog.domain.member.repository.MemberRepository;
import com.example.FixLog.domain.bookmark.repository.BookmarkFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkFolderService {

    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final MemberRepository memberRepository;

    // 북마크 폴더 생성
    public BookmarkFolderCreateResponse createFolder(String folderName, String requesterEmail) {
        Member member = memberRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_EMAIL_NOT_FOUND));

        BookmarkFolder folder = new BookmarkFolder(member, folderName);
        BookmarkFolder saved = bookmarkFolderRepository.save(folder);

        return new BookmarkFolderCreateResponse(saved.getFolderId(), saved.getFolderName()); // 폴더 ID, 폴더 이름 리턴
    }

    // 북마크 폴더 목록 전체 조회
    public PageResponseDto<BookmarkFolderReadResponse> getFoldersByEmail(String email, int page, int size) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_EMAIL_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size);
        Page<BookmarkFolder> folderPage = bookmarkFolderRepository.findAllByUserId(member, pageable);

        return PageResponseDto.from(folderPage, folder -> new BookmarkFolderReadResponse(folder.getFolderId(), folder.getFolderName()));
    }

    // 북마크 폴더 이름 수정
    public void updateFolderName(Long folderId, String email, String newName) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_EMAIL_NOT_FOUND));

        BookmarkFolder folder = bookmarkFolderRepository.findById(folderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FOLDER_NOT_FOUND));

        // 본인만 수정 가능
        if (!folder.getUserId().equals(member)) {
            throw new CustomException(ErrorStatus.ACCESS_DENIED);
        }

        folder.updateName(newName);
    }

    // 북마크 폴더 삭제 -> 기본 폴더는 삭제 불가인지?
    public void deleteFolder(Long folderId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_EMAIL_NOT_FOUND));

        BookmarkFolder folder = bookmarkFolderRepository.findById(folderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FOLDER_NOT_FOUND));


        // 본인만 삭제 가능
        if (!folder.getUserId().equals(member)) {
            throw new CustomException(ErrorStatus.ACCESS_DENIED);
        }

        bookmarkFolderRepository.delete(folder);
    }




}
