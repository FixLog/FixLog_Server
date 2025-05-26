package com.example.FixLog.dto.bookmark.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkFolderReadResponse(
        @JsonProperty("folder_id") Long folderId,
        @JsonProperty("name") String name
) {}

