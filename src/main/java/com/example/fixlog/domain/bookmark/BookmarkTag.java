package com.example.fixlog.domain.bookmark;

import com.example.fixlog.domain.tag.Tag;
import com.example.fixlog.domain.tag.TagCategory;
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
    @Column(name = "bookmark_tag_id",nullable = false)
    private Long id;

    @Column(length = 20, nullable = false)
    private String tag_name;

    @Enumerated(EnumType.STRING)
    private TagCategory tagCategory;
}
