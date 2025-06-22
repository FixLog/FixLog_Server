package com.example.FixLog.controller;

import com.example.FixLog.dto.post.PostRequestDto;
import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.post.PostResponseDto;
import com.example.FixLog.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public Response<Object> createPost(@RequestBody PostRequestDto postRequestDto){
        postService.createPost(postRequestDto);
        return Response.success("게시글 작성 성공.", null);
    }

    @PostMapping("/images")
    public Response<String> uploadImage(@RequestPart("imageFile") MultipartFile imageFile){
        String markdownImage = postService.uploadImage(imageFile);
        return Response.success("이미지 마크다운 형식으로 변환", markdownImage);
    }

    @GetMapping("/{postId}")
    public Response<Object> viewPost(@PathVariable("postId") Long postId){
        PostResponseDto viewPost = postService.viewPost(postId);
        return Response.success("게시글 조회하기 성공", viewPost);
    }

    @PostMapping("/{postId}/like")
    public Response<Object> togglePostLike(@PathVariable("postId") Long postId){
        String message = postService.togglePostLike(postId);
        return Response.success(message, null); // 좋아요 수정하기
    }

    @PostMapping("/{postId}/bookmark")
    public Response<Object> toggleBookmark(@PathVariable("postId") Long postId) {
        String message = postService.toggleBookmark(postId);
        return Response.success(message, null); // 북마크 수정하기
    }
}
