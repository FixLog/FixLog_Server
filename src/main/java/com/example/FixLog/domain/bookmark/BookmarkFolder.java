package com.example.fixlog.domain.bookmark;


import com.example.fixlog.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folderId")
    private Long id;

    @Column(name = "folderName", nullable = false)
    private String name; // 폴더 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Member owner;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    public BookmarkFolder(String name, Member owner) {
        this.name = name;
        this.owner = owner;
    }

    // 폴더 이름 수정 메서드
    public void updateName(String newName) {
        this.name = newName;
    }
}
