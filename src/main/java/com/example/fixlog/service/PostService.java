package com.example.fixlog.service;

import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.post.Post;
import com.example.fixlog.dto.post.PostRequestDto;
import com.example.fixlog.exception.CustomException;
import com.example.fixlog.exception.ErrorCode;
import com.example.fixlog.repository.MemberRepository;
import com.example.fixlog.repository.post.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository){
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public void createPost(PostRequestDto postRequestDto){
        Long userIdInput = postRequestDto.getUserId();
        Member userId = memberRepository.findById(userIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));

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
