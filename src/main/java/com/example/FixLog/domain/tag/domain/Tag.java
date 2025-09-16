package com.example.FixLog.domain.tag.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id",nullable = false)
    private Long tagId;

    @Enumerated(EnumType.STRING)
    private TagCategory tagCategory;

    @Column(length = 50, nullable = false)
    private String tagName;

    private String tagInfo;

    public static Tag of(TagCategory tagCategory, String tagName) {
        Tag tag = new Tag();
        tag.tagCategory = tagCategory;
        tag.tagName = tagName;
        return tag;
    }

    public static Tag of(TagCategory tagCategory, String tagName, String tagInfo) {
        Tag tag = new Tag();
        tag.tagCategory = tagCategory;
        tag.tagName = tagName;
        tag.tagInfo = tagInfo;
        return tag;
    }
}