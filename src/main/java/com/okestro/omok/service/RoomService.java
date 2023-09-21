package com.okestro.omok.service;


import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.payload.dto.AttendeeInfoDto;
import com.okestro.omok.payload.dto.RoomDetailsWithUsersDto;
import com.okestro.omok.payload.dto.RoomInfoDto;
import com.okestro.omok.payload.dto.UserDetailsDto;
import com.okestro.omok.payload.response.RoomDetailsResponse;
import com.okestro.omok.payload.response.RoomDetailsWithUsersResponse;
import com.okestro.omok.repository.RoomRepository;
import com.okestro.omok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public void register(Room room, Long userId) {
        // Room 등록
        roomRepository.save(room);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("USER NOT FOUND"));

        // User에 Room 할당
        user.setRoom(room);

        // User 저장
        userRepository.save(user);
    }

    public AttendeeInfoDto getUserInfo(Long roomId) {
        return new AttendeeInfoDto(roomRepository.findWithUserById(roomId)
                .orElseThrow(() -> new NoSuchElementException("ROOM NOT FOUND")));
    }

    public RoomInfoDto getRoomInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Room room = roomRepository.findById(user.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + user.getRoom().getId()));

        return new RoomInfoDto(room);
    }

    public RoomDetailsResponse findRoomDetails(Long roomId) {

        Room room = findRoom(roomId);

        return RoomDetailsResponse.toEntity(room);
    }

    public List<RoomDetailsWithUsersResponse> findAllRooms(Pageable pageable) {

        List<RoomDetailsWithUsersDto> detailsWithUsers = roomRepository.findDetailsWithUsers(pageable);

        Map<Long, List<UserDetailsDto>> participantRoomInUserMap = preprocessUsersInRoom(detailsWithUsers);

        return preprocessRoomWithUsers(detailsWithUsers, participantRoomInUserMap);
    }

    private List<RoomDetailsWithUsersResponse> preprocessRoomWithUsers(List<RoomDetailsWithUsersDto> detailsWithUsers,
                                                                       Map<Long, List<UserDetailsDto>> participantRoomInUserMap) {
        Map<Long, Object> checkRoomMap = new HashMap<>();

        return detailsWithUsers.stream()
                .filter(user -> checkRoomMap.putIfAbsent(user.getRoomId(), "") == null)
                .map(user -> RoomDetailsWithUsersResponse.toEntity(user, participantRoomInUserMap.get(user.getRoomId())))
                .collect(Collectors.toList());
    }

    private Map<Long, List<UserDetailsDto>> preprocessUsersInRoom(List<RoomDetailsWithUsersDto> detailsWithUsers) {
        return detailsWithUsers.stream()
                .collect(Collectors.groupingBy(
                        RoomDetailsWithUsersDto::getRoomId,
                        Collectors.mapping(UserDetailsDto::toEntity, Collectors.toList())
                ));
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_ROOM));
    }

}