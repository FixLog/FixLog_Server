package com.example.fixlog.dto.bookmark.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkFolderUpdateRequest(
        @JsonProperty("name") String name
) {}

