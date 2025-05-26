package com.example.FixLog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private PostDto postInfo;

    private String nickname;
    private LocalDate createdAt;
    private boolean isLiked;
    private boolean isMarked;
}
