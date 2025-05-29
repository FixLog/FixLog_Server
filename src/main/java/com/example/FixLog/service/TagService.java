package com.example.FixLog.service;

import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.tag.Tag;
import com.example.FixLog.dto.tag.TagDto;
import com.example.FixLog.dto.tag.TagResponseDto;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.tag.TagRepository;
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
    public TagResponseDto viewTags(int page){
        Pageable pageable = PageRequest.of(page - 1, 12);

        Page<Tag> tags = tagRepository.findAll(pageable);

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
