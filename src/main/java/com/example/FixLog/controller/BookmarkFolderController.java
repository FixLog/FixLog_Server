package com.example.fixlog.controller;

import com.example.fixlog.dto.Response;
import com.example.fixlog.dto.bookmark.request.BookmarkFolderCreateRequest;
import com.example.fixlog.dto.bookmark.request.BookmarkFolderUpdateRequest;
import com.example.fixlog.dto.bookmark.request.BookmarkMoveRequest;
import com.example.fixlog.dto.bookmark.response.BookmarkFolderCreateResponse;
import com.example.fixlog.dto.bookmark.response.BookmarkFolderPageResponse;
import com.example.fixlog.service.BookmarkFolderService;
import com.example.fixlog.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        BookmarkFolderCreateResponse response = bookmarkFolderService.createFolder(request.name(), requesterEmail);

        return ResponseEntity.ok(Response.success("북마크 폴더 생성 성공", response));
    }

    // 북마크 폴더 목록 전체 조회
    @GetMapping
    public ResponseEntity<Response<BookmarkFolderPageResponse>> getFolders(
            @RequestParam String requesterEmail,
            @RequestParam int page
    ) {
        BookmarkFolderPageResponse response = bookmarkFolderService.getFoldersByEmail(requesterEmail, page);
        return ResponseEntity.ok(Response.success("북마크 폴더 목록 전체 조회 성공", response));
    }

    // 북마크 폴더 이름 수정
    @PatchMapping("/{folder_id}")
    public ResponseEntity<Response<Void>> updateFolderName(
            @PathVariable Long folderId,
            @RequestParam String requesterEmail,
            @RequestBody BookmarkFolderUpdateRequest request
    ) {
        bookmarkFolderService.updateFolderName(folderId, requesterEmail, request.name());
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


}
