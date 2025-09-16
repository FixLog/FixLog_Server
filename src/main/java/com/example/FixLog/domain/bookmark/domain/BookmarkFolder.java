package com.example.FixLog.domain.bookmark.domain;

import com.example.FixLog.domain.member.domain.Member;
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
    @Column(name = "folder_id")
    private Long folderId;

    @Column(name = "folder_name", nullable = false)
    private String folderName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member userId;

    @OneToMany(mappedBy = "folderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    // 폴더 이름 수정 메서드
    public void updateName(String newName) {
        this.folderName = newName;
    }

    public BookmarkFolder( Member userId, String name) {
        this.userId = userId;
        this.folderName = name;
    }

    public BookmarkFolder(Member userId){
        this.userId = userId;
        this.folderName = "default folder";
    }
}
