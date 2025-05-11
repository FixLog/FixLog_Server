package com.example.fixlog.domain.bookmark;

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
    private Long id;

    private String tagName;

    @Enumerated(EnumType.STRING)
    private TagCategory tagCategory;
}
