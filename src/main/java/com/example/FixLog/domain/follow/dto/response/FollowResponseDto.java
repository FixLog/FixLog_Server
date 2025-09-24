package com.example.FixLog.domain.follow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowResponseDto {
    @JsonProperty("follow_id")
    private Long followId;

    @JsonProperty("following_id")
    private Long followingId;

    private String nickname;
}
