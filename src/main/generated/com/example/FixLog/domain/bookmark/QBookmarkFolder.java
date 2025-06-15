package com.example.FixLog.domain.bookmark;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookmarkFolder is a Querydsl query type for BookmarkFolder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookmarkFolder extends EntityPathBase<BookmarkFolder> {

    private static final long serialVersionUID = 655942016L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookmarkFolder bookmarkFolder = new QBookmarkFolder("bookmarkFolder");

    public final ListPath<Bookmark, QBookmark> bookmarks = this.<Bookmark, QBookmark>createList("bookmarks", Bookmark.class, QBookmark.class, PathInits.DIRECT2);

    public final NumberPath<Long> folderId = createNumber("folderId", Long.class);

    public final StringPath folderName = createString("folderName");

    public final com.example.FixLog.domain.member.QMember userId;

    public QBookmarkFolder(String variable) {
        this(BookmarkFolder.class, forVariable(variable), INITS);
    }

    public QBookmarkFolder(Path<? extends BookmarkFolder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookmarkFolder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookmarkFolder(PathMetadata metadata, PathInits inits) {
        this(BookmarkFolder.class, metadata, inits);
    }

    public QBookmarkFolder(Class<? extends BookmarkFolder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userId = inits.isInitialized("userId") ? new com.example.FixLog.domain.member.QMember(forProperty("userId"), inits.get("userId")) : null;
    }

}

