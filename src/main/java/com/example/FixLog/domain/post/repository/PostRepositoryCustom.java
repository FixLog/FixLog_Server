package com.example.FixLog.domain.post.repository;

import com.example.FixLog.domain.mainPage.dto.search.SearchPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<SearchPostDto> searchByKeywordAndTags(String keyword, List<String> tags, Pageable pageable);
}
