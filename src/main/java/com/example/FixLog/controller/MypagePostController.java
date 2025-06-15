package com.example.FixLog.controller;

import com.example.FixLog.dto.PageResponseDto;
import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.post.MyPostPageResponseDto;
import com.example.FixLog.service.MypagePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypagePostController {

    private final MypagePostService mypagePostService;

    // 내가 쓴 글 보기
    @GetMapping("/posts")
    public ResponseEntity<Response<PageResponseDto<MyPostPageResponseDto>>> getMyPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "0") int sort
    ) {
        String email = userDetails.getUsername();
        PageResponseDto<MyPostPageResponseDto> data = mypagePostService.getMyPosts(email, page, sort, size);
        return ResponseEntity.ok(Response.success("내가 작성한 글 보기 성공", data));
    }

   // 내가 좋아요한 글
   @GetMapping("/likes")
   public ResponseEntity<Response<PageResponseDto<MyPostPageResponseDto>>> getLikedPosts(
           @AuthenticationPrincipal UserDetails userDetails,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "4") int size,
           @RequestParam(defaultValue = "0") int sort) {

       String email = userDetails.getUsername();
       PageResponseDto<MyPostPageResponseDto> result = mypagePostService.getLikedPosts(email, page, sort, size);
       return ResponseEntity.ok(Response.success("내가 좋아요한 글 보기 성공", result));
   }


}
