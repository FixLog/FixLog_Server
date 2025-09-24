package com.example.FixLog.domain.bookmark.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkFolderUpdateRequest(
        @JsonProperty("name") String folderName
) {}

