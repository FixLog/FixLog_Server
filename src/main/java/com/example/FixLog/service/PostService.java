package com.example.FixLog.service;

import com.example.FixLog.domain.bookmark.Bookmark;
import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.like.PostLike;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.post.Post;
import com.example.FixLog.domain.post.PostTag;
import com.example.FixLog.domain.tag.Tag;
import com.example.FixLog.domain.tag.TagCategory;
import com.example.FixLog.dto.post.PostDto;
import com.example.FixLog.dto.post.PostRequestDto;
import com.example.FixLog.dto.post.PostResponseDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.bookmark.BookmarkFolderRepository;
import com.example.FixLog.repository.bookmark.BookmarkRepository;
import com.example.FixLog.repository.like.PostLikeRepository;
import com.example.FixLog.repository.post.PostRepository;
import com.example.FixLog.repository.tag.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TagRepository tagRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final MemberService memberService;
    private final S3Service s3Service;

    public PostService(PostRepository postRepository, PostLikeRepository postLikeRepository,
                       BookmarkRepository bookmarkRepository, TagRepository tagRepository,
                       BookmarkFolderRepository bookmarkFolderRepository, MemberService memberService,
                       S3Service s3Service){
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.tagRepository = tagRepository;
        this.bookmarkFolderRepository = bookmarkFolderRepository;
        this.memberService = memberService;
        this.s3Service = s3Service;
    }

    // 이미지 null일 때 default 사진으로 변경 - 프로필 사진
    public String getDefaultProfile(String image){
        String imageUrl = (image == null || image.isBlank())
                ? "https://fixlogsmwubucket.s3.ap-northeast-2.amazonaws.com/default/DefaultImage.png" : image;
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

    // 게시글 작성하기
    @Transactional
    public void createPost(PostRequestDto postRequestDto){
        Member member = memberService.getCurrentMemberInfo();
        String coverImageUrl = postRequestDto.getCoverImageUrl();

        // 북마크 카테고리별로 선택 제한 두기
        List<Tag> tags = fetchAndValidateTags(postRequestDto.getTags());

        // 게시글 발행
        validatePost(postRequestDto); // 필수 항목 다 입력되었는지 확인
        Post newPost = Post.builder()
                .userId(member)
                .postTitle(postRequestDto.getPostTitle())
                .coverImage(coverImageUrl)
                .problem(postRequestDto.getProblem())
                .errorMessage(postRequestDto.getErrorMessage())
                .environment(postRequestDto.getEnvironment())
                .reproduceCode(postRequestDto.getReproduceCode())
                .solutionCode(postRequestDto.getSolutionCode())
                .causeAnalysis(postRequestDto.getCauseAnalysis())
                .referenceLink(postRequestDto.getReferenceLink())
                .extraContent(postRequestDto.getExtraContent())
                .createdAt(LocalDateTime.now())
                .editedAt(LocalDateTime.now())
                .postTags(new ArrayList<>())
                .build();
        // Todo : 여기서 사진 발생하면 s3 처리하기

        // 태그 저장
        for (Tag tag : tags) {
            PostTag postTag = new PostTag(newPost, tag);
            newPost.getPostTags().add(postTag);
        }
        postRepository.save(newPost);
    }

    // 태그 다 선택 했는지
    private List<Tag> fetchAndValidateTags(List<String> tagNames){
        // 태그 이름으로 Tag 엔티티 조회
        List<Tag> tags = tagNames.stream()
                .map(tagName -> tagRepository.findByTagName(tagName)
                        .orElseThrow(() -> new CustomException(ErrorCode.TAG_NOT_FOUND)))
                .toList();

        // 태그 종류별로 그룹화
        Map<TagCategory, List<Tag>> tagCategoryMap = tags.stream()
                .collect(Collectors.groupingBy(Tag::getTagCategory));

        TagCategory[] requiredTypes = TagCategory.values();
        List<String> issues = new ArrayList<>();

        // 하나라도 빠졌다면 예외 처리
        for (TagCategory type : requiredTypes){
            List<Tag> categories = tagCategoryMap.get(type);

            if (type == TagCategory.MINOR_CATEGORY){ // 소분류는 4개까지 선택 가능
                if (categories == null)
                    issues.add(type.name() + "태그가 선택되지 않았습니다.");
                else if (categories.size() > 4)
                    issues.add(type.name() + "태그는 최대 4개까지 선택 가능합니다.");

            } else {
                if (categories == null)
                    issues.add(type.name() + "태그가 선택되지 않았습니다.");
                else if (categories.size() > 1)
                    issues.add(type.name() + "태그는 하나만 선택해야 합니다.");
            }
        }

        if (!issues.isEmpty()) {
            String message = String.join(" / ", issues);
            throw new CustomException(ErrorCode.REQUIRED_TAGS_MISSING, message);
        }
        return tags;
    }

    // 게시글 필수 항목 다 작성했는지
    private void validatePost(PostRequestDto postRequestDto){
        if (!StringUtils.hasText(postRequestDto.getPostTitle())
                || !StringUtils.hasText(postRequestDto.getProblem())
                || !StringUtils.hasText(postRequestDto.getErrorMessage())
                || !StringUtils.hasText(postRequestDto.getEnvironment())
                || !StringUtils.hasText(postRequestDto.getReproduceCode())
                || !StringUtils.hasText(postRequestDto.getSolutionCode()))
            throw new CustomException(ErrorCode.REQUIRED_CONTENT_MISSING);
    }

    // 이미지 파일 마크다운으로 변경
    @Transactional
    public String uploadImage(MultipartFile imageFile){
        SecurityContextHolder.getContext().getAuthentication();

        if (imageFile == null || imageFile.isEmpty()){
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }

        String imageUrl = s3Service.upload(imageFile, "post-image");
        return "![image](" + imageUrl + ")";
    }

    // 게시글 수정하기
    @Transactional
    public void editPost(Long postId, PostRequestDto postRequestDto) {
        Member member = memberService.getCurrentMemberInfo();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 게시글 작성자가 본인이 맞는지
        if (!member.getUserId().equals(post.getUserId().getUserId())) {
            throw new CustomException(ErrorCode.POST_UPDATE_FORBIDDEN);
        }

        // 북마크 카테고리별로 선택 제한 두기
        List<Tag> tags = fetchAndValidateTags(postRequestDto.getTags());

        // 아무것도 변경이 없으면 예외처리
        if (Objects.equals(post.getPostTitle(), postRequestDto.getPostTitle())
            && Objects.equals(post.getCoverImage(), postRequestDto.getCoverImageUrl())
            && Objects.equals(post.getProblem(), postRequestDto.getProblem())
            && Objects.equals(post.getErrorMessage(), postRequestDto.getErrorMessage())
            && Objects.equals(post.getEnvironment(), postRequestDto.getEnvironment())
            && Objects.equals(post.getReproduceCode(), postRequestDto.getReproduceCode())
            && Objects.equals(post.getSolutionCode(), postRequestDto.getSolutionCode())
            && Objects.equals(post.getCauseAnalysis(), postRequestDto.getCauseAnalysis())
            && Objects.equals(post.getReferenceLink(), postRequestDto.getReferenceLink())
            && Objects.equals(post.getExtraContent(), postRequestDto.getExtraContent())
            && compareTags(post.getPostTags(), tags)){
            throw new CustomException(ErrorCode.NO_CONTENT_CHANGED);
        }

        // 필드 업데이트
        validatePost(postRequestDto);

        post.changeTitle(postRequestDto.getPostTitle());
        post.changeCoverImage(postRequestDto.getCoverImageUrl());
        post.changeProblem(postRequestDto.getProblem());
        post.changeErrorMessage(postRequestDto.getErrorMessage());
        post.changeEnvironment(postRequestDto.getEnvironment());
        post.changeReproduceCode(postRequestDto.getReproduceCode());
        post.changeSolutionCode(postRequestDto.getSolutionCode());
        post.changeCauseAnalysis(postRequestDto.getCauseAnalysis());
        post.changeReferenceLink(postRequestDto.getReferenceLink());
        post.changeExtraContent(postRequestDto.getExtraContent());
        post.updateEditedAt(LocalDateTime.now());

        // 태그 저장
        post.clearTags(); // 기존 태그 다 제거
        for (Tag tag : tags) {
            PostTag postTag = new PostTag(post, tag);
            post.getPostTags().add(postTag);
        }
    }

    private boolean compareTags(List<PostTag> currentPostTags, List<Tag> newTags) {
        List<Tag> currentTags = currentPostTags.stream()
                .map(PostTag::getTagId)
                .toList();

        return new HashSet<>(currentTags).equals(new HashSet<>(newTags));
    }

    // 게시글 조회하기
    public PostResponseDto viewPost(Long postId){
        Optional<Member> optionalMember = memberService.getCurrentOptionalMemberInfo();

        Post currentPost = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        PostDto postInfo = new PostDto(
                currentPost.getUserId().getUserId(),
                currentPost.getUserId().getNickname(),
                currentPost.getPostTitle(),
                getDefaultCover(currentPost.getCoverImage()),
                currentPost.getProblem(),
                currentPost.getErrorMessage(),
                currentPost.getEnvironment(),
                currentPost.getReproduceCode(),
                currentPost.getSolutionCode(),
                currentPost.getCauseAnalysis(),
                currentPost.getReferenceLink(),
                currentPost.getExtraContent(),
                currentPost.getPostTags().stream()
                        .map(postTag -> postTag.getTagId().getTagName())
                        .collect(toList())
        );

        String nickname; String profileImageUrl;
        boolean isLiked; boolean isMarked;
        if (optionalMember.isPresent()){
            Member member = optionalMember.get();
            nickname = member.getNickname();
            String imageUrl = member.getProfileImageUrl();
            profileImageUrl = getDefaultProfile(imageUrl);

            isLiked = currentPost.getPostLikes().stream()
                    .anyMatch(postLike -> postLike.getUserId().equals(member));
            isMarked = currentPost.getBookmarks().stream()
                    .anyMatch(bookmark -> bookmark.getFolderId().getUserId().equals(member));
        } else {
            nickname = "로그인하지 않았습니다.";
            profileImageUrl = "https://fixlogsmwubucket.s3.ap-northeast-2.amazonaws.com/default/DefaultImage.png"; // 비로그인 기본 이미지
            isLiked = false;
            isMarked = false;
        }

        LocalDate createdAt = currentPost.getCreatedAt().toLocalDate();

        return new PostResponseDto(postInfo, createdAt, nickname, profileImageUrl, isLiked, isMarked);
    }

    // 게시글 좋아요
    public String togglePostLike(Long postIdInput){
        Member member = memberService.getCurrentMemberInfo();

        Post postId = postRepository.findById(postIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Optional<PostLike> optionalLike = postLikeRepository.findByUserIdAndPostId(member, postId);

        if (optionalLike.isEmpty()){ // 객체 없는 경우
            PostLike newLike = new PostLike(member, postId);
            postLikeRepository.save(newLike);
            return "게시글 좋아요 성공";
        } else { // 객체 있는 경우
            PostLike postLike = optionalLike.get();
            postLike.ToggleLike(!postLike.isLiked());
            postLikeRepository.save(postLike);
            if (postLike.isLiked())
                return "게시글 좋아요 성공";
            else
                return "게시글 좋아요 삭제 성공";
        }
    }

    // 게시글 북마크
    public String toggleBookmark(Long postIdInput){
        Member member = memberService.getCurrentMemberInfo();
        Post postId = postRepository.findById(postIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        BookmarkFolder folderId = bookmarkFolderRepository.findByUserId(member); // 이 코드는 폴더가 하나일 때만 적용됨
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByFolderIdAndPostId(folderId, postId);

        // 본인 글은 북마크 못하도록
        if (member == postId.getUserId())
            throw new CustomException(ErrorCode.SELF_BOOKMARK_NOT_ALLOWED);
        // 북마크 처리
        if (optionalBookmark.isEmpty()){ // 객체 없는 경우
            Bookmark newBookmark = new Bookmark(folderId, postId);
            bookmarkRepository.save(newBookmark);
            return "게시글 북마크 성공";
        } else { // 객체 있는 경우
            Bookmark bookmark = optionalBookmark.get();
            bookmark.ToggleBookmark(!bookmark.isMarked());
            bookmarkRepository.save(bookmark);
            System.out.println(bookmark.isMarked());
            return (bookmark.isMarked()) ? "게시글 북마크 성공" : "게시글 북마크 삭제 성공";
        }
    }
}
