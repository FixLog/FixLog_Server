package com.example.fixlog.controller;

import com.example.fixlog.dto.Response;
import com.example.fixlog.dto.bookmark.request.BookmarkFolderCreateRequest;
import com.example.fixlog.dto.bookmark.response.BookmarkFolderCreateResponse;
import com.example.fixlog.service.BookmarkFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark-folders")
public class BookmarkFolderController {
    private final BookmarkFolderService bookmarkFolderService;

    // 북마크 폴더 생성
    @PostMapping
    public ResponseEntity<?> createFolder(
            @RequestBody BookmarkFolderCreateRequest request,
            @RequestParam String requesterEmail
    ) {
        BookmarkFolderCreateResponse response = bookmarkFolderService.createFolder(request.name(), requesterEmail);

        return ResponseEntity.ok(Response.success("북마크 폴더 생성 성공", response));
    }

    // 북마크 폴더 

}
