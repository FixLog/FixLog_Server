package com.example.FixLog.service;

import com.example.FixLog.domain.bookmark.Bookmark;
import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.post.Post;
import com.example.FixLog.dto.PageResponseDto;
import com.example.FixLog.dto.post.MyPostPageResponseDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.bookmark.BookmarkFolderRepository;
import com.example.FixLog.repository.bookmark.BookmarkRepository;
import com.example.FixLog.repository.fork.ForkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final ForkRepository forkRepository;

    // 북마크 폴더 이동
    public void moveBookmarkToFolder(Long bookmarkId, Long newFolderId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUND));

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

    // 특정 폴더의 북마크 목록
    public PageResponseDto<MyPostPageResponseDto> getBookmarksInFolder(String email, Long folderId, int page, int sort, int size) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUND));

        BookmarkFolder folder = bookmarkFolderRepository.findById(folderId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOLDER_NOT_FOUND));

        // 1: 오래된순, 0: 최신순
        Sort.Direction direction = (sort == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "postId.createdAt"));

        Page<Bookmark> bookmarkPage = bookmarkRepository.findByFolderId(folder, pageable);
        List<Post> bookmarkedPosts = bookmarkPage.map(Bookmark::getPostId).getContent();

        // fork count 한번에 조회
        Map<Long, Integer> forkCountMap = forkRepository.countForksByOriginalPosts(bookmarkedPosts)
                .stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> ((Long) row[1]).intValue()
                ));

        return PageResponseDto.from(bookmarkPage.map(Bookmark::getPostId), post ->
                MyPostPageResponseDto.from(post, forkCountMap.getOrDefault(post.getPostId(), 0))
        );
    }

}
