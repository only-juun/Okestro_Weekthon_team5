package com.okestro.omok.payload.response;

import com.okestro.omok.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomIdResponse {

    private Long roomId;


    @Builder
    private RoomIdResponse(Long roomId) {
        this.roomId = roomId;
    }

    public static RoomIdResponse toEntity(Room room) {
        return RoomIdResponse.builder()
                .roomId(room.getId())
                .build();
    }
}
