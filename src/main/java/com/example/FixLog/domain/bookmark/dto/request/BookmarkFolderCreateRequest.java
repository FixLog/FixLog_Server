package com.example.FixLog.domain.bookmark.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkFolderCreateRequest(
        @JsonProperty("name") String folderName
) {}

