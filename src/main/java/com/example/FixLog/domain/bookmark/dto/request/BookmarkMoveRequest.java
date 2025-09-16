package com.example.FixLog.domain.bookmark.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkMoveRequest(
        @JsonProperty("folder_id") Long folderId
) {}
