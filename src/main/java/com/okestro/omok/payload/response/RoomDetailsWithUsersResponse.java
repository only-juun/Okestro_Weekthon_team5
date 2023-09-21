package com.okestro.omok.payload.response;

import com.okestro.omok.payload.dto.RoomDetailsWithUsersDto;
import com.okestro.omok.payload.dto.UserDetailsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class RoomDetailsWithUsersResponse {

    private Long roomId;

    private String restaurantCategory;

    private LocalDateTime lunchTime;

    private String title;

    private String restaurantName;

    private String restaurantLocation;

    private Integer limitedAttendees;

    private List<UserDetailsDto> userDetails;

    @Builder
    private RoomDetailsWithUsersResponse(Long roomId, String restaurantCategory, LocalDateTime lunchTime, String title, String restaurantName, String restaurantLocation, Integer limitedAttendees, List<UserDetailsDto> userDetails) {
        this.roomId = roomId;
        this.restaurantCategory = restaurantCategory;
        this.lunchTime = lunchTime;
        this.title = title;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.limitedAttendees = limitedAttendees;
        this.userDetails = userDetails;
    }

    public static RoomDetailsWithUsersResponse toEntity(RoomDetailsWithUsersDto roomDetailsWithUsers, List<UserDetailsDto> userDetails) {
        return RoomDetailsWithUsersResponse.builder()
                .roomId(roomDetailsWithUsers.getRoomId())
                .restaurantCategory(roomDetailsWithUsers.getRestaurantCategory())
                .lunchTime(roomDetailsWithUsers.getLunchTime())
                .title(roomDetailsWithUsers.getTitle())
                .restaurantName(roomDetailsWithUsers.getRestaurantName())
                .restaurantLocation(roomDetailsWithUsers.getRestaurantLocation())
                .limitedAttendees(roomDetailsWithUsers.getLimitedAttendees())
                .userDetails(userDetails)
                .build();
    }

}
