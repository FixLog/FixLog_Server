package com.example.FixLog.controller;

import com.example.FixLog.dto.UserIdDto;
import com.example.FixLog.dto.post.PostRequestDto;
import com.example.FixLog.dto.Response;
import com.example.FixLog.service.PostService;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/{postId}")
//    public Response<postResponseDto> viewPost(@RequestParam Long postId){
//        return Response.success("게시글 조회하기 성공", postService.viewPost(postId));
//    }

    @PostMapping("/{postId}/like")
    public Response<Object> togglePostLike(@PathVariable("postId") Long postId,
                                           @RequestBody UserIdDto userIdDto){
        String message = postService.togglePostLike(postId, userIdDto);
        return Response.success(message, null);
    }

    @PostMapping("/{postId}/bookmark")
    public Response<Object> toggleBookmark(@PathVariable("postId") Long postId,
                                           @RequestBody UserIdDto userIdDto) {
        String message = postService.toggleBookmark(postId, userIdDto);
        return Response.success(message, null);
    }
}
