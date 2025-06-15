package com.example.FixLog.controller;

import com.example.FixLog.dto.PageResponseDto;
import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.search.SearchPostDto;
import com.example.FixLog.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public Response<PageResponseDto<SearchPostDto>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SearchPostDto> result = searchService.searchPosts(keyword, tags, pageable);

        PageResponseDto<SearchPostDto> responseDto = PageResponseDto.from(result, dto -> dto);
        return Response.success("검색 성공", responseDto);
    }
}