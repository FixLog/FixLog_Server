package com.example.FixLog.service;

import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.post.Post;
import com.example.FixLog.dto.main.MainPagePostResponseDto;
import com.example.FixLog.dto.main.MainPageResponseDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainPageService {
    private final PostRepository postRepository;
    private final MemberService memberService;

    public MainPageService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
    }

    // 이미지 null일 때 default 사진으로 변경 (프로필 사진,
    public String getDefaultImage(String image){
        String imageUrl = (image == null || image.isBlank())
                ? "https://example.com/default-cover-image.png" : image;
        System.out.println(imageUrl);
        return imageUrl;
    }

    // 메인페이지 보기
    public MainPageResponseDto mainPageView(int sort){
        // 사용자 정보 불러오기
        Member member = memberService.getCurrentMemberInfo();
        String imageUrl = member.getProfileImageUrl();
        String profileImageUrl = getDefaultImage(imageUrl);

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
                        getDefaultImage(post.getCoverImage()),
                        post.getPostTags().stream()
                                .map(postTag -> postTag.getTagId().getTagName())
                                .collect(Collectors.toList()),
                        getDefaultImage(post.getUserId().getProfileImageUrl()),
                        post.getUserId().getNickname(),
                        post.getCreatedAt().toLocalDate(),
                        post.getPostLikes().size()
                ))
                .collect(Collectors.toList());

        return new MainPageResponseDto(profileImageUrl, postList);
    }

    // 메인페이지 전체보기
    public MainPageResponseDto mainPageFullView(int sort, int page){
        // 사용자 정보 불러오기
        Member member = memberService.getCurrentMemberInfo();
        String imageUrl = member.getProfileImageUrl();
        String profileImageUrl = getDefaultImage(imageUrl);

        // 페이지 설정 (한 페이지당 12개)
        Pageable pageable = PageRequest.of(page - 1, 12);
        Page<Post> postPage;

        if (sort == 0) { // 최신순 정렬
            postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else if (sort == 1) { // 인기순 정렬
            postPage = postRepository.findAllByOrderByPostLikesDesc(pageable);
        } else
            throw new CustomException(ErrorCode.SORT_NOT_EXIST);

        List<MainPagePostResponseDto> postList = postPage.stream()
                .map(post -> new MainPagePostResponseDto(
                        post.getPostTitle(),
                        getDefaultImage(post.getCoverImage()),
                        post.getPostTags().stream()
                                .map(postTag -> postTag.getTagId().getTagName())
                                .collect(Collectors.toList()),
                        getDefaultImage(post.getUserId().getProfileImageUrl()),
                        post.getUserId().getNickname(),
                        post.getCreatedAt().toLocalDate(),
                        post.getPostLikes().size()
                ))
                .collect(Collectors.toList());

        int totalPages = postPage.getTotalPages(); // 전체 페이지 수 출력

        return new MainPageResponseDto(profileImageUrl, postList, totalPages);
    }
}
