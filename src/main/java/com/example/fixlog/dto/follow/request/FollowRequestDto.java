package com.example.fixlog.dto.follow.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowRequestDto {
    @JsonProperty("target_member_id")
    private Long targetMemberId;  // 팔로우 대상 ID
}
