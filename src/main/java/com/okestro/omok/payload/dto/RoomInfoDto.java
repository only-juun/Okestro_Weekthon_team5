package com.okestro.omok.payload.dto;

import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RoomInfoDto {
//    private Long id;
    private String title;
//    private String description;
    private String restaurantName;
//    private String restaurantLocation;
//    private String restaurantCategory;
//    private Integer limitedAttendees;
    private LocalDateTime lunchTime;

    public RoomInfoDto(Room room) {
//        this.id = room.getId();
        this.title = room.getTitle();
        this.restaurantName = room.getRestaurantName();
        this.lunchTime = room.getLunchTime();
    }
}
