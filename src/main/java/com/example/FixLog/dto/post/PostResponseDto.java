package com.example.FixLog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private PostDto postInfo;
    private LocalDate createdAt;

    private String nickname;
    private String profileImageUrl;
    private boolean isLiked;
    private boolean isMarked;
}
