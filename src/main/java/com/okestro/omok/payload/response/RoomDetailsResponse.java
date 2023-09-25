package com.okestro.omok.payload.response;

import com.okestro.omok.domain.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class RoomDetailsResponse {

    private String title;

    private String description;

    private String restaurantCategory;

    private String locationUrl;

    private String restaurantName;

    private String restaurantLocation;

    private String lunchTime;

    private Double restaurantLatitude;

    private Double restaurantLongitude;


    @Builder
    private RoomDetailsResponse(String title, String description, String restaurantCategory, String locationUrl, String restaurantName, String restaurantLocation, String lunchTime, Double restaurantLatitude, Double restaurantLongitude) {
        this.title = title;
        this.description = description;
        this.restaurantCategory = restaurantCategory;
        this.locationUrl = locationUrl;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.lunchTime = lunchTime;
        this.restaurantLatitude = restaurantLatitude;
        this.restaurantLongitude = restaurantLongitude;
    }

    public static RoomDetailsResponse toEntity(Room room) {
        return RoomDetailsResponse.builder()
                .title(room.getTitle())
                .description(room.getDescription())
                .restaurantCategory(room.getRestaurantCategory())
                .restaurantName(room.getRestaurantName())
                .locationUrl(room.getLocationUrl())
                .restaurantLocation(room.getRestaurantLocation())
                .lunchTime(String.valueOf(room.getLunchTime()))
                .restaurantLongitude(room.getRestaurantLongitude())
                .restaurantLatitude(room.getRestaurantLatitude())
                .build();
    }
}
