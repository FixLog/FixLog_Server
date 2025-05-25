package com.example.FixLog.dto.bookmark.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkFolderUpdateRequest(
        @JsonProperty("name") String folderName
) {}

