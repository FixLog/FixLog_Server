package com.example.FixLog.dto.follow.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnfollowRequestDto {
    @JsonProperty("target_member_id")
    private Long targetMemberId;
}
