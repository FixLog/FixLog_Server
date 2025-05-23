package com.example.fixlog.service;

import com.example.fixlog.domain.bookmark.Bookmark;
import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.like.PostLike;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.post.Post;
import com.example.fixlog.domain.post.PostTag;
import com.example.fixlog.domain.tag.Tag;
import com.example.fixlog.dto.UserIdDto;
import com.example.fixlog.dto.post.PostRequestDto;
import com.example.fixlog.exception.CustomException;
import com.example.fixlog.exception.ErrorCode;
import com.example.fixlog.repository.MemberRepository;
import com.example.fixlog.repository.bookmark.BookmarkFolderRepository;
import com.example.fixlog.repository.bookmark.BookmarkRepository;
import com.example.fixlog.repository.like.PostLikeRepository;
import com.example.fixlog.repository.post.PostRepository;
import com.example.fixlog.repository.tag.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TagRepository tagRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository,
                       PostLikeRepository postLikeRepository, BookmarkRepository bookmarkRepository,
                       TagRepository tagRepository, BookmarkFolderRepository bookmarkFolderRepository){
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.postLikeRepository = postLikeRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.tagRepository = tagRepository;
        this.bookmarkFolderRepository = bookmarkFolderRepository;
    }

    // 게시글 생성하기
    @Transactional
    public void createPost(PostRequestDto postRequestDto){
        Long userIdInput = postRequestDto.getUserId();
        Member userId = memberRepository.findById(userIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));

        String coverImageUrl = postRequestDto.getCoverImageUrl();
        if (coverImageUrl == null || coverImageUrl.isBlank())
            coverImageUrl = "url";

        // Todo : 북마크 카테고리별로 선택 제한 두기

        // 게시글 발행
        Post newPost = new Post(
                userId,
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
//    public postResponseDto viewPost(@RequestParam Long postId){
//        return new postResponseDto;
//    }

    // 게시글 좋아요
    public void togglePostLike(Long postIdInput, UserIdDto userIdDto){
        Long userIdInput = userIdDto.getUserId();
        Member userId = memberRepository.findById(userIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));

        Post postId = postRepository.findById(postIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Optional<PostLike> optionalLike = postLikeRepository.findByMemberAndPost(userId, postId);

        if (optionalLike.isEmpty()){ // 객체 없는 경우
            PostLike newLike = new PostLike(userId, postId);
            postLikeRepository.save(newLike);
        } else { // 객체 있는 경우
            PostLike postLike = optionalLike.get();
            postLike.ToggleLike(!postLike.isLiked());
            postLikeRepository.save(postLike);
            // Todo : 좋아요 한 건지 삭제한 건지 message로 알 수 있게 하면 좋을듯
        }
    }

    // 게시글 북마크
    public void toggleBookmark(Long postIdInput, UserIdDto userIdDto){
        Long userIdInput = userIdDto.getUserId();
        Member userId = memberRepository.findById(userIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));

        Post postId = postRepository.findById(postIdInput)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        BookmarkFolder folderId = bookmarkFolderRepository.findByOwner(userId); // 이 코드는 폴더가 하나일 때만 적용됨
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByFolderAndPost(folderId, postId);

        if (optionalBookmark.isEmpty()){ // 객체 없는 경우
            Bookmark newBookmark = new Bookmark(folderId, postId);
            bookmarkRepository.save(newBookmark);
        } else { // 객체 있는 경우
            Bookmark bookmark = optionalBookmark.get();
            bookmark.ToggleBookmark(!bookmark.isMarked());
            bookmarkRepository.save(bookmark);
            // Todo : 북마크 한 건지 삭제한 건지 message로 알 수 있게 하면 좋을듯
        }

        // Fixme : 본인 글은 북마크 못하려나?
    }
}
