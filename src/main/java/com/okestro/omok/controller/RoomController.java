package com.okestro.omok.controller;


import com.okestro.omok.domain.Room;
import com.okestro.omok.payload.dto.RoomDetailsWithUsersDto;
import com.okestro.omok.payload.request.RoomSaveRequestDto;
import com.okestro.omok.payload.response.RoomDetailsResponse;
import com.okestro.omok.payload.response.RoomDetailsWithUsersResponse;
import com.okestro.omok.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    /**
     * 방 등록
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestHeader("X-USER-TOKEN") Long userToken,
            @Valid @RequestBody RoomSaveRequestDto roomSaveRequestDto) {
        Room room = Room.builder()
                .title(roomSaveRequestDto.getTitle())
                .description(roomSaveRequestDto.getDescription())
                .restaurantName(roomSaveRequestDto.getRestaurantName())
                .restaurantLocation(roomSaveRequestDto.getRestaurantLocation())
                .restaurantCategory(roomSaveRequestDto.getRestaurantCategory())
                .lunchTime(roomSaveRequestDto.getLunchTime())
                .limitedAttendees(roomSaveRequestDto.getLimitedAttendees())
                .restaurantLatitude(roomSaveRequestDto.getRestaurantLatitude())
                .restaurantLongitude(roomSaveRequestDto.getRestaurantLongitude())
                .build();

        Long userId = roomSaveRequestDto.getUserId();

        roomService.register(room, userId);
    }

    /**
     * 방 참가자 목록 조회: 사용자 정보 필요
     */
    @GetMapping("/{roomId}/users")
    public ResponseEntity getUserList(
            @RequestHeader("X-USER-TOKEN") Long userToken,
            @PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok(roomService.getUserInfo(roomId));
    }


    /**
     * 참가 중인 방 상세 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity getRoomDetail(
            @RequestHeader("X-USER-TOKEN") Long userToken,
            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(roomService.getRoomInfo(userId));
    }


    @GetMapping("/{roomId}/details")
    public ResponseEntity<RoomDetailsResponse> findRoomDetails(
            @RequestHeader("X-USER-TOKEN") Long userToken,
            @PathVariable("roomId") Long roomId) {

        return ResponseEntity
                .ok(roomService.findRoomDetails(roomId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDetailsWithUsersResponse>> findAllRoom(
            @RequestHeader("X-USER-TOKEN") Long userToken,
            @PageableDefault(size = 10, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity
                .ok(roomService.findAllRooms(pageable));
    }
}