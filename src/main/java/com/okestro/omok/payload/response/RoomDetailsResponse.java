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

    private String lunchTimeHour;

    private String lunchTimeMinute;

    @Builder
    private RoomDetailsResponse(String title, String description, String restaurantCategory, String restaurantName, String restaurantLocation, String lunchTimeHour, String lunchTimeMinute) {
        this.title = title;
        this.description = description;
        this.restaurantCategory = restaurantCategory;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.lunchTimeHour = lunchTimeHour;
        this.lunchTimeMinute = lunchTimeMinute;
    }

    public static RoomDetailsResponse toEntity(Room room) {
        return RoomDetailsResponse.builder()
                .title(room.getTitle())
                .description(room.getDescription())
                .restaurantCategory(room.getRestaurantCategory())
                .restaurantName(room.getRestaurantName())
                .restaurantLocation(room.getRestaurantLocation())
                .lunchTimeHour(String.valueOf(room.getLunchTime().getHour()))
                .lunchTimeMinute(String.valueOf(room.getLunchTime().getMinute()))
                .build();
    }
}
