package com.example.FixLog.domain.post;

import com.example.FixLog.domain.post.dto.PostRequestDto;
import com.example.FixLog.common.exception.Response;
import com.example.FixLog.domain.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    // 게시글 작성하기
    @PostMapping
    public Response<Object> createPost(@RequestBody PostRequestDto postRequestDto){
        postService.createPost(postRequestDto);
        return Response.success("게시글 작성 성공.", null);
    }

    // 이미지 마크다운 형식으로 변환하기
    @PostMapping("/images")
    public Response<String> uploadImage(@RequestPart("imageFile") MultipartFile imageFile){
        String markdownImage = postService.uploadImage(imageFile);
        return Response.success("이미지 마크다운 형식으로 변환", markdownImage);
    }

    // 게시글 수정하기
    @PatchMapping("/{postId}/edit")
    public Response<Object> editPost(@PathVariable("postId") Long postId,
                                     @RequestBody PostRequestDto postRequestDto){
        postService.editPost(postId, postRequestDto);
        return Response.success("게시글 수정 성공.", null);
    }

    // 게시글 보기
    @GetMapping("/{postId}")
    public Response<Object> viewPost(@PathVariable("postId") Long postId){
        PostResponseDto viewPost = postService.viewPost(postId);
        return Response.success("게시글 조회하기 성공", viewPost);
    }

    // 좋아요 누르기/취소하기
    @PostMapping("/{postId}/like")
    public Response<Object> togglePostLike(@PathVariable("postId") Long postId){
        String message = postService.togglePostLike(postId);
        return Response.success(message, null); // 좋아요 수정하기
    }

    // 북마크 누르기/취소하기
    @PostMapping("/{postId}/bookmark")
    public Response<Object> toggleBookmark(@PathVariable("postId") Long postId) {
        String message = postService.toggleBookmark(postId);
        return Response.success(message, null); // 북마크 수정하기
    }
}
