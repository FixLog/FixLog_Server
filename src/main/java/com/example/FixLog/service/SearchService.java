package com.example.FixLog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import com.example.FixLog.dto.search.SearchPostDto;

public interface SearchService {
    Page<SearchPostDto> searchPosts(String keyword, List<String> tags, Pageable pageable);
}