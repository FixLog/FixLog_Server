package com.example.fixlog.mock;

import com.example.fixlog.domain.tag.Tag;
import com.example.fixlog.domain.tag.TagCategory;
import com.example.fixlog.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagTestDataInitializer implements CommandLineRunner {

    private final TagRepository tagRepository;

    @Override
    public void run(String... args) {
        if (tagRepository.count() == 0) {
            Tag tag1 = Tag.of(TagCategory.valueOf("대분류"), "101");
            Tag tag2 = Tag.of(TagCategory.valueOf("대분류"), "101");
            Tag tag3 = Tag.of(TagCategory.valueOf("중분류"), "201");
            Tag tag4 = Tag.of(TagCategory.valueOf("소분류"), "301");
            tagRepository.saveAll(List.of(tag1, tag2, tag3, tag4));
            System.out.println("임시 태그 4개 삽입 완료");
        }
    }
}
