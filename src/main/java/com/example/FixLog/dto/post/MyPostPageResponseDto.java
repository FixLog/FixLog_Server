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
    private String profileImageUrl;;

    // 이미지 null일 때 default 사진으로 변경 - 프로필 사진
    public static String getDefaultProfile(String image){
        String imageUrl = (image == null || image.isBlank())
                ? "https://fixlog-bucket.s3.ap-northeast-2.amazonaws.com/default/profile.png" : image;
        System.out.println(imageUrl);
        return imageUrl;
    }
    // 이미지 null일 때 default 사진으로 변경 - 썸네일
    public static String getDefaultCover(String image){
        String imageUrl = (image == null || image.isBlank())
                ? "https://fixlogsmwubucket.s3.ap-northeast-2.amazonaws.com/default/DefaulThumnail.png" : image;
        System.out.println(imageUrl);
        return imageUrl;
    }

    public static MyPostPageResponseDto from(Post post, int forkCount) {
        return MyPostPageResponseDto.builder()
                .postId(post.getPostId())
                .nickname(post.getUserId().getNickname())
                .postTitle(post.getPostTitle())
                .postSummary(generateSummary(post.getProblem()))
                .imageUrl(getDefaultCover(post.getCoverImage()))
                .profileImageUrl(getDefaultProfile(post.getUserId().getProfileImageUrl()))
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
