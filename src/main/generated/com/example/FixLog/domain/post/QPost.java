package com.example.FixLog.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1578031282L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final ListPath<com.example.FixLog.domain.bookmark.Bookmark, com.example.FixLog.domain.bookmark.QBookmark> bookmarks = this.<com.example.FixLog.domain.bookmark.Bookmark, com.example.FixLog.domain.bookmark.QBookmark>createList("bookmarks", com.example.FixLog.domain.bookmark.Bookmark.class, com.example.FixLog.domain.bookmark.QBookmark.class, PathInits.DIRECT2);

    public final StringPath causeAnalysis = createString("causeAnalysis");

    public final StringPath coverImage = createString("coverImage");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> editedAt = createDateTime("editedAt", java.time.LocalDateTime.class);

    public final StringPath environment = createString("environment");

    public final StringPath errorMessage = createString("errorMessage");

    public final StringPath extraContent = createString("extraContent");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final ListPath<com.example.FixLog.domain.like.PostLike, com.example.FixLog.domain.like.QPostLike> postLikes = this.<com.example.FixLog.domain.like.PostLike, com.example.FixLog.domain.like.QPostLike>createList("postLikes", com.example.FixLog.domain.like.PostLike.class, com.example.FixLog.domain.like.QPostLike.class, PathInits.DIRECT2);

    public final ListPath<PostTag, QPostTag> postTags = this.<PostTag, QPostTag>createList("postTags", PostTag.class, QPostTag.class, PathInits.DIRECT2);

    public final StringPath postTitle = createString("postTitle");

    public final StringPath problem = createString("problem");

    public final StringPath referenceLink = createString("referenceLink");

    public final StringPath reproduceCode = createString("reproduceCode");

    public final StringPath solutionCode = createString("solutionCode");

    public final com.example.FixLog.domain.member.QMember userId;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userId = inits.isInitialized("userId") ? new com.example.FixLog.domain.member.QMember(forProperty("userId"), inits.get("userId")) : null;
    }

}

