package com.okestro.omok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = -485892827L;

    public static final QRoom room = new QRoom("room");

    public final QBaseTime _super = new QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> limitedAttendees = createNumber("limitedAttendees", Integer.class);

    public final StringPath locationUrl = createString("locationUrl");

    public final DateTimePath<java.time.LocalDateTime> lunchTime = createDateTime("lunchTime", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath restaurantCategory = createString("restaurantCategory");

    public final NumberPath<Double> restaurantLatitude = createNumber("restaurantLatitude", Double.class);

    public final StringPath restaurantLocation = createString("restaurantLocation");

    public final NumberPath<Double> restaurantLongitude = createNumber("restaurantLongitude", Double.class);

    public final StringPath restaurantName = createString("restaurantName");

    public final StringPath title = createString("title");

    public final ListPath<User, QUser> users = this.<User, QUser>createList("users", User.class, QUser.class, PathInits.DIRECT2);

    public QRoom(String variable) {
        super(Room.class, forVariable(variable));
    }

    public QRoom(Path<? extends Room> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoom(PathMetadata metadata) {
        super(Room.class, metadata);
    }

}

