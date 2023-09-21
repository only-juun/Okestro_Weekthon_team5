package com.okestro.omok.repository.querydsl;

import com.okestro.omok.domain.QRoom;
import com.okestro.omok.domain.QUser;
import com.okestro.omok.payload.dto.QRoomDetailsWithUsersDto;
import com.okestro.omok.payload.dto.RoomDetailsWithUsersDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class RoomRepositoryImpl implements RoomRepositoryCustom{

    private static final QUser qUser = QUser.user;
    private static final QRoom qRoom = QRoom.room;

    private final JPAQueryFactory jpaQueryFactory;

    public RoomRepositoryImpl(EntityManager entityManager) {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<RoomDetailsWithUsersDto> findDetailsWithUsers(Pageable pageable) {
        return jpaQueryFactory
                .select(
                        new QRoomDetailsWithUsersDto(
                                qRoom.id,
                                qRoom.restaurantCategory,
                                qRoom.lunchTime,
                                qRoom.title,
                                qRoom.restaurantName,
                                qRoom.restaurantLocation,
                                qRoom.limitedAttendees,
                                qUser.id,
                                qUser.image
                        )
                ).from(qRoom)
                .leftJoin(qRoom.users,qUser)
                .where(qRoom.deletedAt.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
