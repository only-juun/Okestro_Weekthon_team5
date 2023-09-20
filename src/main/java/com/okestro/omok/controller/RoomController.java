package com.okestro.omok.controller;


import com.okestro.omok.domain.Room;
import com.okestro.omok.payload.request.RoomSaveRequestDto;
import com.okestro.omok.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    /**
     * 방 등록
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RoomSaveRequestDto roomSaveRequestDto) {
        Room room = Room.builder()
                .title(roomSaveRequestDto.getTitle())
                .description(roomSaveRequestDto.getDescription())
                .restaurantName(roomSaveRequestDto.getRestaurantName())
                .restaurantLocation(roomSaveRequestDto.getRestaurantLocation())
                .restaurantCategory(roomSaveRequestDto.getRestaurantCategory())
                .lunchTime(roomSaveRequestDto.getLunchTime())
                .limitedAttendees(roomSaveRequestDto.getLimitedAttendees())
                .build();

        roomService.register(room);
    }
}


