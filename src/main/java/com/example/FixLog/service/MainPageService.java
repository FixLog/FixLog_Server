package com.example.FixLog.service;

import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.post.Post;
import com.example.FixLog.domain.post.PostTag;
import com.example.FixLog.dto.UserIdDto;
import com.example.FixLog.dto.main.MainPagePostResponseDto;
import com.example.FixLog.dto.main.MainPageResponseDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainPageService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public MainPageService(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    // 회원 정보 불러오기
    public Member getMemberOrThrow(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));
    }

    // 메인페이지 보기
    public MainPageResponseDto mainPageView(int sort, UserIdDto userIddto){
        // 사용자 정보 불러오기
        Member member = getMemberOrThrow(userIddto.getUserId());
        String profileImageUrl = member.getProfileImageUrl();

        // 페이지 (글 12개) 불러오기
        List<Post> posts;

        if (sort == 0) { // 최신순 정렬
            posts = postRepository.findTop12ByOrderByCreatedAtDesc();
        } else if (sort == 1) { // 인기순 정렬
            posts = postRepository.findTop12ByOrderByPostLikesDesc();
        } else
            throw new CustomException(ErrorCode.SORT_NOT_EXIST);

        List<MainPagePostResponseDto> postList = posts.stream()
                .map(post -> new MainPagePostResponseDto(
                        post.getPostTitle(),
                        post.getCoverImage(),
                        post.getPostTags(), // Todo : 여기서 무한루프로 불러오는 거 해결해야 함
                        post.getUserId().getProfileImageUrl(),
                        post.getUserId().getNickname(),
                        post.getCreatedAt().toLocalDate()
                ))
                .collect(Collectors.toList());

        return new MainPageResponseDto(profileImageUrl, postList);
    }

    // 메인페이지 전체보기
//    public MainPageResponseDto mainPageFullView(int sort, int page, UserIdDto userIddto){
//        // 사용자 정보 불러오기
//        Member member = getMemberOrThrow(userIddto.getUserId());
//        String profileImageUrl = member.getProfileImageUrl();
//
//        // 페이지 (글 12개) 불러오기
//        List<MainPagePostResponseDto> posts;
//
//        if (sort == 0) { // 최신순 정렬
//            posts = postRepository.findTop12ByOrderByCreatedAtDesc();
//        } else if (sort == 1) { // 인기순 정렬
//            posts = postRepository.findTop12ByOrderByPostLikesDesc();
//        } else
//            throw new CustomException(ErrorCode.SORT_NOT_EXIST);
//
//        return new MainPageResponseDto(profileImageUrl, posts);
//    }
}
