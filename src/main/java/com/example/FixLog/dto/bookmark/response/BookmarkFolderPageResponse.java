package com.example.fixlog.dto.bookmark.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BookmarkFolderPageResponse(
        @JsonProperty("content") List<BookmarkFolderReadResponse> content,
        @JsonProperty("page") int page,
        @JsonProperty("size") int size,
        @JsonProperty("total_pages") int totalPages,
        @JsonProperty("total_elements") long totalElements
) {}

