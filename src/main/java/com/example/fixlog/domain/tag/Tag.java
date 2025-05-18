package com.example.fixLog.domain.tag;

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
    @Column(name = "tagId",nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TagCategory tagCategory;

    @Column(length = 20, nullable = false)
    private String tag_name;
}