package com.example.FixLog.domain.mainPage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import com.example.FixLog.domain.mainPage.dto.search.SearchPostDto;

public interface SearchService {
    Page<SearchPostDto> searchPosts(String keyword, List<String> tags, Pageable pageable);
}