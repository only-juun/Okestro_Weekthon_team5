package com.okestro.omok.repository.querydsl;

import com.okestro.omok.domain.QRoom;
import com.okestro.omok.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class RoomRepositoryImpl implements RoomRepositoryCustom{

    private static final QUser qUser = QUser.user;
    private static final QRoom qRoom = QRoom.room;

    private final JPAQueryFactory jpaQueryFactory;

    public RoomRepositoryImpl(EntityManager entityManager) {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }


}
