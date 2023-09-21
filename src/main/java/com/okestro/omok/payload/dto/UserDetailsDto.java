package com.okestro.omok.payload.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDetailsDto {

    private Long userId;

    private String userImage;

    @Builder
    private UserDetailsDto(Long userId, String userImage) {
        this.userId = userId;
        this.userImage = userImage;
    }

    public static UserDetailsDto toEntity(RoomDetailsWithUsersDto roomDetailsWithUser) {
        return UserDetailsDto.builder()
                .userId(roomDetailsWithUser.getUserId())
                .userImage(roomDetailsWithUser.getUserImage())
                .build();
    }
}