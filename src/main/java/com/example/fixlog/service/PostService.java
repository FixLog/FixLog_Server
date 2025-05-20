package com.example.fixlog.service;

import com.example.fixlog.domain.like.PostLike;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.post.Post;
import com.example.fixlog.dto.UserIdDto;
import com.example.fixlog.dto.post.PostRequestDto;
import com.example.fixlog.exception.CustomException;
import com.example.fixlog.exception.ErrorCode;
import com.example.fixlog.repository.MemberRepository;
import com.example.fixlog.repository.like.PostLikeRepository;
import com.example.fixlog.repository.post.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository, PostLikeRepository postLikeRepository){
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.postLikeRepository = postLikeRepository;
    }

    // 게시글 생성하기
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

    // 게시글 조회하기
//    public postResponseDto viewPost(@RequestParam Long postId){
//        return new postResponseDto;
//    }

    // 게시글 좋아요
    public void postLike(Long postIdInput, UserIdDto userIdDto){
        Long userIdInput = userIdDto.getUserId();
        Member userId = memberRepository.findById(userIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));

        Post postId = postRepository.findById(postIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (postLikeRepository.findByMemberAndPost(userId, postId).isPresent())
                throw new CustomException(ErrorCode.POST_LIKE_ALREADY);

        PostLike postLike = new PostLike(userId, postId);
        postLikeRepository.save(postLike);
    }

    // 게시글 좋아요 삭제
    public void postUnlike(Long postIdInput, UserIdDto userIdDto){
        Long userIdInput = userIdDto.getUserId();
        Member userId = memberRepository.findById(userIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));

        Post postId = postRepository.findById(postIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        PostLike postLike = postLikeRepository.findByMemberAndPost(userId, postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_LIKE_NOT_FOUND));
        postLikeRepository.delete(postLike);
    }

    // 게시글 북마크


    // 게시글 북마크 삭제
}
