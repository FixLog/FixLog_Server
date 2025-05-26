package com.example.fixlog.dto.bookmark.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkFolderReadResponse(
        @JsonProperty("folder_id") Long folderId,
        @JsonProperty("name") String name
) {}

