package com.example.fixlog.controller.post;

import com.example.fixlog.dto.UserIdDto;
import com.example.fixlog.dto.post.PostRequestDto;
import com.example.fixlog.dto.Response;
import com.example.fixlog.service.PostService;
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

    @PostMapping("/{postId}/likes")
    public Response<Object> postLike(@PathVariable("postId") Long postId,
                                     @RequestBody UserIdDto userIdDto){
        postService.postLike(postId, userIdDto);
        return Response.success("게시글 좋아요 성공", null);
    }

    @DeleteMapping("/{postId}/likes")
    public Response<Object> postUnlike(@PathVariable("postId") Long postId,
                                       @RequestBody UserIdDto userIdDto){
        postService.postUnlike(postId, userIdDto);
        return Response.success("게시글 좋아요 삭제 성공", null);
    }
}
