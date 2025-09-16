package com.example.FixLog.domain.bookmark.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkFolderReadResponse(
        @JsonProperty("folder_id") Long folderId,
        @JsonProperty("name") String name
) {}

