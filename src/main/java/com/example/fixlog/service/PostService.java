package com.example.fixlog.service;

import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.post.Post;
import com.example.fixlog.dto.post.PostRequestDto;
import com.example.fixlog.repository.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public void createPost(@RequestBody Member userId, @RequestBody PostRequestDto postRequestDto){
        Post newPost = new Post(
                userId,
                postRequestDto.getPostTitle(),
                postRequestDto.getProblem(),
                postRequestDto.getErrorMessage(),
                postRequestDto.getEnvironment(),
                postRequestDto.getReproduceCode(),
                postRequestDto.getSolutionCode(),
                postRequestDto.getCauseAnalysis(),
                postRequestDto.getReferenceLink(),
                postRequestDto.getExtraContent(),
                LocalDateTime.now(),
                LocalDateTime.now()
        ); postRepository.save(newPost);
    }

//    public postResponseDto viewPost(@RequestParam Long postId){
//        return new postResponseDto;
//    }
}
