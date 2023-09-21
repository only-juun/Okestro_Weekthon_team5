package com.okestro.omok.payload.response;

import com.okestro.omok.domain.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomDetailsResponse {

    private String title;

    private String description;

    private String restaurantCategory;

    private String restaurantName;

    private String restaurantLocation;

    private String lunchTime;

    @Builder
    private RoomDetailsResponse(String title, String description, String restaurantCategory, String restaurantName, String restaurantLocation, String lunchTime) {
        this.title = title;
        this.description = description;
        this.restaurantCategory = restaurantCategory;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.lunchTime = lunchTime;
    }

    public static RoomDetailsResponse toEntity(Room room) {
        return RoomDetailsResponse.builder()
                .title(room.getTitle())
                .description(room.getDescription())
                .restaurantCategory(room.getRestaurantCategory())
                .restaurantName(room.getRestaurantName())
                .restaurantLocation(room.getRestaurantLocation())
                .lunchTime(String.valueOf(room.getLunchTime()))
                .build();
    }
}
