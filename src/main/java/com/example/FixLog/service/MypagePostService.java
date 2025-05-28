package com.example.FixLog.service;

import com.example.FixLog.domain.like.PostLike;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.post.Post;
import com.example.FixLog.dto.PageResponseDto;
import com.example.FixLog.dto.post.MyPostPageResponseDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.fork.ForkRepository;
import com.example.FixLog.repository.like.PostLikeRepository;
import com.example.FixLog.repository.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MypagePostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ForkRepository forkRepository;
    private final PostLikeRepository postLikeRepository;

    public MypagePostService(PostRepository postRepository, MemberRepository memberRepository, ForkRepository forkRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.forkRepository = forkRepository;
        this.postLikeRepository = postLikeRepository;
    }

    // 내가 쓴 글 보기
    public PageResponseDto<MyPostPageResponseDto> getMyPosts(String email, int page, int sort, int size) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 1: 오래된순, 0: 최신순
        Sort.Direction direction = (sort == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createdAt"));

        Page<Post> postPage = postRepository.findByUserId(member, pageable);
        List<Post> posts = postPage.getContent();

        // fork count 한번에 조회
        List<Object[]> forkCounts = forkRepository.countForksByOriginalPosts(posts);
        Map<Long, Integer> forkCountMap = forkCounts.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],           // postId
                        row -> ((Long) row[1]).intValue() // 포크 카운트 (int)
                ));

        return PageResponseDto.from(postPage, post ->
                MyPostPageResponseDto.from(post, forkCountMap.getOrDefault(post.getPostId(), 0) // 없으면 0 반환
                )
        );
    }

    // 내가 좋아요한 글 보기
    public PageResponseDto<MyPostPageResponseDto> getLikedPosts(String email, int page, int sort, int size) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 1: 오래된순, 0: 최신순
        Sort.Direction direction = (sort == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "postId.createdAt"));

        Page<PostLike> postLikePage = postLikeRepository.findByUserId(member, pageable);
        List<Post> likedPosts = postLikePage.map(PostLike::getPostId).getContent();

        // fork count 한번에 조회
        List<Object[]> forkCounts = forkRepository.countForksByOriginalPosts(likedPosts);
        Map<Long, Integer> forkCountMap = forkCounts.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> ((Long) row[1]).intValue()
                ));

        return PageResponseDto.from(postLikePage.map(PostLike::getPostId), post ->
                MyPostPageResponseDto.from(post, forkCountMap.getOrDefault(post.getPostId(), 0))
        );
    }


}
