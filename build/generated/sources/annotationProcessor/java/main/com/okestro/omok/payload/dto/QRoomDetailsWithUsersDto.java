package com.okestro.omok.payload.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.okestro.omok.payload.dto.QRoomDetailsWithUsersDto is a Querydsl Projection type for RoomDetailsWithUsersDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRoomDetailsWithUsersDto extends ConstructorExpression<RoomDetailsWithUsersDto> {

    private static final long serialVersionUID = 226748885L;

    public QRoomDetailsWithUsersDto(com.querydsl.core.types.Expression<Long> roomId, com.querydsl.core.types.Expression<String> restaurantCategory, com.querydsl.core.types.Expression<java.time.LocalDateTime> lunchTime, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> restaurantName, com.querydsl.core.types.Expression<String> restaurantLocation, com.querydsl.core.types.Expression<Integer> limitedAttendees, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> userImage) {
        super(RoomDetailsWithUsersDto.class, new Class<?>[]{long.class, String.class, java.time.LocalDateTime.class, String.class, String.class, String.class, int.class, long.class, String.class}, roomId, restaurantCategory, lunchTime, title, restaurantName, restaurantLocation, limitedAttendees, userId, userImage);
    }

}

