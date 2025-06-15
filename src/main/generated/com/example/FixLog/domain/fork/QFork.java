package com.example.FixLog.domain.fork;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFork is a Querydsl query type for Fork
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFork extends EntityPathBase<Fork> {

    private static final long serialVersionUID = 1327242738L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFork fork = new QFork("fork");

    public final com.example.FixLog.domain.post.QPost forkedPostId;

    public final NumberPath<Long> forkId = createNumber("forkId", Long.class);

    public final com.example.FixLog.domain.post.QPost originalPostId;

    public final com.example.FixLog.domain.member.QMember userId;

    public QFork(String variable) {
        this(Fork.class, forVariable(variable), INITS);
    }

    public QFork(Path<? extends Fork> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFork(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFork(PathMetadata metadata, PathInits inits) {
        this(Fork.class, metadata, inits);
    }

    public QFork(Class<? extends Fork> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.forkedPostId = inits.isInitialized("forkedPostId") ? new com.example.FixLog.domain.post.QPost(forProperty("forkedPostId"), inits.get("forkedPostId")) : null;
        this.originalPostId = inits.isInitialized("originalPostId") ? new com.example.FixLog.domain.post.QPost(forProperty("originalPostId"), inits.get("originalPostId")) : null;
        this.userId = inits.isInitialized("userId") ? new com.example.FixLog.domain.member.QMember(forProperty("userId"), inits.get("userId")) : null;
    }

}

