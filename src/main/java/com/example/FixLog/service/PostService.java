package com.example.FixLog.service;

import com.example.FixLog.domain.bookmark.Bookmark;
import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.domain.like.PostLike;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.post.Post;
import com.example.FixLog.domain.post.PostTag;
import com.example.FixLog.domain.tag.Tag;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TagRepository tagRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final MemberService memberService;

    public PostService(PostRepository postRepository, PostLikeRepository postLikeRepository,
                       BookmarkRepository bookmarkRepository, TagRepository tagRepository,
                       BookmarkFolderRepository bookmarkFolderRepository, MemberService memberService){
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.tagRepository = tagRepository;
        this.bookmarkFolderRepository = bookmarkFolderRepository;
        this.memberService = memberService;
    }

    // 이미지 null일 때 default 사진으로 변경 (프로필 사진,
    public String getDefaultImage(String image){
        String imageUrl = (image == null || image.isBlank())
                ? "https://example.com/default-cover-image.png" : image;
        System.out.println(imageUrl);
        return imageUrl;
    }

    // 게시글 생성하기
    @Transactional
    public void createPost(PostRequestDto postRequestDto){
        Member member = memberService.getCurrentMemberInfo();

        String coverImageUrl = postRequestDto.getCoverImageUrl();

        // Todo : 북마크 카테고리별로 선택 제한 두기

        // 게시글 발행
        Post newPost = new Post(
                member,
                postRequestDto.getPostTitle(),
                coverImageUrl,
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
        );

        // 태그 저장
        List<Long> tagIds = postRequestDto.getTags(); // 이제 Long ID 목록임
        for (Long tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new CustomException(ErrorCode.TAG_NOT_FOUND));
            PostTag postTag = new PostTag(newPost, tag);
            newPost.getPostTags().add(postTag);
        }

        postRepository.save(newPost);
    }

    // 게시글 조회하기
    public PostResponseDto viewPost(Long postId){
        Member member = memberService.getCurrentMemberInfo();

        Post currentPost = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        PostDto postInfo = new PostDto(
                currentPost.getPostTitle(),
                getDefaultImage(currentPost.getCoverImage()),
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
                        .collect(Collectors.toList())
        );

        String nickname = member.getNickname();
        LocalDate createdAt = currentPost.getCreatedAt().toLocalDate();
        boolean isLiked = currentPost.getPostLikes().stream()
                .anyMatch(postLike -> postLike.getUserId().equals(member));
        boolean isMarked = currentPost.getBookmarks().stream()
                .anyMatch(bookmark -> bookmark.getFolderId().getUserId().equals(member));

        return new PostResponseDto(postInfo, nickname, createdAt, isLiked, isMarked);
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
        if (member == folderId.getUserId())
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
