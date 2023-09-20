package com.okestro.omok.controller;


import com.okestro.omok.domain.Room;
import com.okestro.omok.payload.request.RoomSaveRequestDto;
import com.okestro.omok.repository.RoomRepository;
import com.okestro.omok.repository.UserRepository;
import com.okestro.omok.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    /**
     * 방 등록
     */
    @PostMapping("")
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

        Long userId = roomSaveRequestDto.getUserId();

        roomService.register(room, userId);
    }

    /**
     * 방 참가자 목록 조회: 사용자 정보 필요
     */
    @GetMapping("/{roomId}/users")
    public ResponseEntity getUserList(@PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok(roomService.getUserInfo(roomId));
    }


    /**
     * 참가 중인 방 상세 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity getRoomDetail(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(roomService.getRoomInfo(userId));
    }
}


