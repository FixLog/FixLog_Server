package com.example.FixLog.dto.post;

import com.example.FixLog.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPostPageResponseDto {
    private Long postId;
    private String postTitle;
    private String postSummary;
    private String imageUrl;
    private List<String> tags;
    private LocalDateTime createdAt;
    private int likeCount;
    private int forkCount;
    private String nickname;

    public static MyPostPageResponseDto from(Post post, int forkCount) {
        return MyPostPageResponseDto.builder()
                .postId(post.getPostId())
                .nickname(post.getUserId().getNickname())
                .postTitle(post.getPostTitle())
                .postSummary(generateSummary(post.getProblem()))
                .imageUrl(post.getCoverImage())
                .tags(post.getPostTags().stream().map(tag -> tag.getTagId().getTagName()).toList())
                .createdAt(post.getCreatedAt())
                .likeCount(post.getPostLikes().size())
                .forkCount(forkCount)
                .build();
    }

    private static String generateSummary(String content) {
        if (content == null) return "";
        return content.length() > 200 ? content.substring(0, 200) + "..." : content;
    }
}
