package com.example.fixlog.dto.follow.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowerListResponseDto {
    @JsonProperty("follow_id")
    private Long followId;

    @JsonProperty("follower_id")
    private Long followerId;

    private String nickname;
}
