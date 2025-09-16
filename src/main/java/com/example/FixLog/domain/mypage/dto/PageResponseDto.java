package com.example.FixLog.domain.mypage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponseDto<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer totalPages;

    private PageResponseDto(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber() + 1; // 1부터 시작
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages() == 0 ? null : page.getTotalPages();
    }

    public static <T, R> PageResponseDto<R> from(Page<T> page, Function<T, R> mapper) {
        Page<R> mapped = page.map(mapper);
        return new PageResponseDto<>(mapped);
    }
}

