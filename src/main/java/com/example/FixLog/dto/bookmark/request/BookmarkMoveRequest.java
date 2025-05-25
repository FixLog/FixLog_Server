package com.example.fixlog.dto.bookmark.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkMoveRequest(
        @JsonProperty("folder_id") Long folderId
) {}
