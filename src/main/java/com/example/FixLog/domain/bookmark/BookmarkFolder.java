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
    @Column(name = "folder_id")
    private Long folderId;

    @Column(name = "folder_name", nullable = false)
    private String folderName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member userId;

    @OneToMany(mappedBy = "folderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

<<<<<<< HEAD
    public BookmarkFolder(String name, Member owner) {
        this.name = name;
        this.owner = owner;
    }

    // 폴더 이름 수정 메서드
    public void updateName(String newName) {
        this.name = newName;
=======
    public BookmarkFolder(Member userId){
        this.userId = userId;
        this.folderName = "default folder";
>>>>>>> b32ddb2b758a53e321c9ae679c23589f56f3b63c
    }
}
