package com.example.fixlog.dto.follow.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowingListResponseDto {
    @JsonProperty("follow_id")
    private Long followId;

    @JsonProperty("following_id")
    private Long followingId;

    private String nickname;
}
