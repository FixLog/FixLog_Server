package com.example.FixLog.domain.bookmark;

import com.example.FixLog.domain.mypage.dto.PageResponseDto;
import com.example.FixLog.common.exception.Response;
import com.example.FixLog.domain.bookmark.dto.request.BookmarkFolderCreateRequest;
import com.example.FixLog.domain.bookmark.dto.request.BookmarkFolderUpdateRequest;
import com.example.FixLog.domain.bookmark.dto.request.BookmarkMoveRequest;
import com.example.FixLog.domain.bookmark.dto.response.BookmarkFolderCreateResponse;
import com.example.FixLog.domain.bookmark.dto.response.BookmarkFolderReadResponse;
import com.example.FixLog.domain.post.dto.MyPostPageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark-folders")
public class BookmarkFolderController {
    private final BookmarkFolderService bookmarkFolderService;
    private final BookmarkService bookmarkService;

    // 북마크 폴더 생성
    @PostMapping
    public ResponseEntity<?> createFolder(
            @RequestBody BookmarkFolderCreateRequest request,
            @RequestParam String requesterEmail
    ) {
        BookmarkFolderCreateResponse response = bookmarkFolderService.createFolder(request.folderName(), requesterEmail);

        return ResponseEntity.ok(Response.success("북마크 폴더 생성 성공", response));
    }

    // 북마크 폴더 이름 수정
    @PatchMapping("/{folder_id}")
    public ResponseEntity<Response<Void>> updateFolderName(
            @PathVariable Long folderId,
            @RequestParam String requesterEmail,
            @RequestBody BookmarkFolderUpdateRequest request
    ) {
        bookmarkFolderService.updateFolderName(folderId, requesterEmail, request.folderName());
        return ResponseEntity.ok(Response.success("폴더 이름 수정 완료", null));
    }

    // 북마크 폴더 이동
    @PatchMapping("/{bookmarkId}/move")
    public ResponseEntity<Response<Void>> moveBookmark(
            @PathVariable Long bookmarkId,
            @RequestParam String requesterEmail,
            @RequestBody BookmarkMoveRequest request
    ) {
        bookmarkService.moveBookmarkToFolder(bookmarkId, request.folderId(), requesterEmail);
        return ResponseEntity.ok(Response.success("북마크 다른 폴더로 이동 성공", null));
    }


    // 북마크 폴더 삭제
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Response<Void>> deleteFolder(
            @PathVariable Long folderId,
            @RequestParam String requesterEmail
    ) {
        bookmarkFolderService.deleteFolder(folderId, requesterEmail);
        return ResponseEntity.ok(Response.success("북마크 폴더 삭제 완료", null));
    }

    // 북마크 폴더 목록 전체 조회 - MVP
    @GetMapping
    public ResponseEntity<Response<PageResponseDto<BookmarkFolderReadResponse>>> getFolders(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        String email = userDetails.getUsername();
        PageResponseDto<BookmarkFolderReadResponse> response = bookmarkFolderService.getFoldersByEmail(email, page, size);
        return ResponseEntity.ok(Response.success("북마크 폴더 목록 전체 조회 성공", response));
    }

    // 특정 폴더의 북마크 목록 조회 -MVP
    @GetMapping("/{folderId}/bookmarks")
    public ResponseEntity<Response<PageResponseDto<MyPostPageResponseDto>>> getBookmarksByFolder(
            @PathVariable Long folderId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "0") int sort
    ) {
        String email = userDetails.getUsername();
        PageResponseDto<MyPostPageResponseDto> data = bookmarkService.getBookmarksInFolder(email, folderId, page, sort, size);
        return ResponseEntity.ok(Response.success("특정 폴더의 북마크 목록 조회 성공", data));
    }


}
