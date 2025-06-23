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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MainPageService {
    private final PostRepository postRepository;
    private final MemberService memberService;

    public MainPageService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

    // 이미지 null일 때 default 사진으로 변경 - 프로필 사진
    public String getDefaultProfile(String image){
        String imageUrl = (image == null || image.isBlank())
                ? "https://fixlog-bucket.s3.ap-northeast-2.amazonaws.com/default/profile.png" : image;
        System.out.println(imageUrl);
        return imageUrl;
    }
    // 이미지 null일 때 default 사진으로 변경 - 썸네일
    public String getDefaultCover(String image){
        String imageUrl = (image == null || image.isBlank())
                ? "https://fixlogsmwubucket.s3.ap-northeast-2.amazonaws.com/default/DefaulThumnail.png" : image;
        System.out.println(imageUrl);
        return imageUrl;
    }

    // 메인페이지 보기
    public MainPageResponseDto mainPageView(int sort, int size){
        // 사용자 정보 불러오기
        Optional<Member> optionalMember = memberService.getCurrentOptionalMemberInfo();
        String profileImageUrl;

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String imageUrl = member.getProfileImageUrl();
            profileImageUrl = getDefaultProfile(imageUrl);
        } else {
            profileImageUrl = "https://fixlog-bucket.s3.ap-northeast-2.amazonaws.com/default/profile.png"; // 비로그인 기본 이미지
        }

        // 페이지 (글 12개) 불러오기
        Page<Post> posts;
        Sort sortOption;

        if (sort == 0) { // 최신순 정렬
            sortOption = Sort.by(Sort.Direction.DESC, "createdAt");
        } else if (sort == 1) { // 인기순 정렬
            // Todo : 이거 정렬할 때 좋아요 0인거 이상하고, 이거랑 연결해서인지 totalpages 계산도 이상하게 됨 
            sortOption = Sort.by(Sort.Direction.DESC, "postLikes");
        } else
            throw new CustomException(ErrorCode.SORT_NOT_EXIST);

        Pageable pageable = PageRequest.of(0, size, sortOption);
        posts = postRepository.findAll(pageable);

        List<MainPagePostResponseDto> postList = posts.stream()
                .map(post -> new MainPagePostResponseDto(
                        post.getPostTitle(),
                        getDefaultCover(post.getCoverImage()),
                        post.getPostTags().stream()
                                .map(postTag -> postTag.getTagId().getTagName())
                                .collect(Collectors.toList()),
                        getDefaultProfile(post.getUserId().getProfileImageUrl()),
                        post.getUserId().getNickname(),
                        post.getCreatedAt().toLocalDate(),
                        post.getPostLikes().size()
                ))
                .collect(Collectors.toList());

        return new MainPageResponseDto(profileImageUrl, postList);
    }

    // 메인페이지 전체보기
    public MainPageResponseDto mainPageFullView(int sort, int page, int size){
        // 사용자 정보 불러오기
        Optional<Member> optionalMember = memberService.getCurrentOptionalMemberInfo();
        String profileImageUrl;

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String imageUrl = member.getProfileImageUrl();
            profileImageUrl = getDefaultProfile(imageUrl);
        } else {
            profileImageUrl = "https://fixlog-bucket.s3.ap-northeast-2.amazonaws.com/default/profile.png"; // 비로그인 기본 이미지
        }

        // 페이지 설정 (한 페이지당 12개)
        Pageable pageable = PageRequest.of(page - 1, size);
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
                        getDefaultCover(post.getCoverImage()),
                        post.getPostTags().stream()
                                .map(postTag -> postTag.getTagId().getTagName())
                                .collect(Collectors.toList()),
                        getDefaultProfile(post.getUserId().getProfileImageUrl()),
                        post.getUserId().getNickname(),
                        post.getCreatedAt().toLocalDate(),
                        post.getPostLikes().size()
                ))
                .collect(Collectors.toList());

        int totalPages = postPage.getTotalPages(); // 전체 페이지 수 출력

        return new MainPageResponseDto(profileImageUrl, postList, totalPages);
    }
}
