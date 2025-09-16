package com.example.FixLog.domain.tag;

import com.example.FixLog.domain.tag.domain.Tag;
import com.example.FixLog.domain.tag.domain.TagCategory;
import com.example.FixLog.domain.tag.dto.TagDto;
import com.example.FixLog.domain.tag.dto.TagResponseDto;
import com.example.FixLog.domain.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    // 태그 모음 보기
    public TagResponseDto viewTags(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Tag> tags = tagRepository.findAllByTagCategory(TagCategory.MINOR_CATEGORY, pageable);

        List<TagDto> tagList = tags.stream()
                .map(tag -> new TagDto(
                        tag.getTagName(),
                        tag.getTagInfo().length() > 100
                                ? tag.getTagInfo().substring(0, 100) + "..." : tag.getTagInfo()
                ))
                .collect(Collectors.toList());

        int totalPages = tags.getTotalPages();

        return new TagResponseDto(tagList, totalPages);
    }
}
