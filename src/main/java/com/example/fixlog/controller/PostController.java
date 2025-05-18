package com.example.fixlog.controller;

import com.example.fixlog.dto.Response;
import com.example.fixlog.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/{postId}")
    public Response<Object> makePost(@RequestParam Long postId){
        postService.makePost(postId);
        return Response.success("게시글 작성에 성공했습니다.", null);
    }
}
