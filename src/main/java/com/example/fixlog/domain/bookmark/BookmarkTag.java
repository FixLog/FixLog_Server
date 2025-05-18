package com.example.fixLog.domain.bookmark;

import com.example.fixLog.domain.tag.TagCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmarkTagId",nullable = false)
    private Long id;

    @Column(length = 20, nullable = false)
    private String tagName;

    @Enumerated(EnumType.STRING)
    private TagCategory tagCategory;
}
