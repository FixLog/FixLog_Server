package com.example.FixLog.domain.tag;

import lombok.Getter;

@Getter
public enum TagCategory {
    BIG_CATEGORY("분류"),
    MAJOR_CATEGORY("대분류"),
    MIDDLE_CATEGORY("중분류"),
    MINOR_CATEGORY("소분류");

    private final String displayName;

    TagCategory(String displayName) {
       this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
