package com.okestro.omok.payload.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoomDetailsWithUsersDto {

    private Long roomId;

    private String restaurantCategory;

    private LocalDateTime lunchTime;

    private String title;

    private String restaurantName;

    private String restaurantLocation;

    private Integer limitedAttendees;

    private Long userId;

    private String userImage;

    @QueryProjection
    public RoomDetailsWithUsersDto(Long roomId, String restaurantCategory, LocalDateTime lunchTime, String title, String restaurantName, String restaurantLocation, Integer limitedAttendees, Long userId, String userImage) {
        this.roomId = roomId;
        this.restaurantCategory = restaurantCategory;
        this.lunchTime = lunchTime;
        this.title = title;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.limitedAttendees = limitedAttendees;
        this.userId = userId;
        this.userImage = userImage;
    }
}
