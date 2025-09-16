package com.example.FixLog.domain.tag;

import com.example.FixLog.common.exception.Response;
import com.example.FixLog.domain.tag.dto.TagResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    @GetMapping
    public Response<Object> viewTags(@RequestParam("page") int page,
                                     @RequestParam("size") int size){
        TagResponseDto tags = tagService.viewTags(page, size);
        return Response.success("태그 모아보기 성공", tags);
    }
}
