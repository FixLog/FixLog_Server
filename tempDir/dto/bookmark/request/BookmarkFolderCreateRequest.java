package com.example.fixlog.dto.bookmark.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkFolderCreateRequest(
        @JsonProperty("name") String folderName
) {}

