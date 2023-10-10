package com.okestro.omok.service;


import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.payload.dto.AttendeeInfoDto;
import com.okestro.omok.payload.dto.RoomDetailsWithUsersDto;
import com.okestro.omok.payload.dto.RoomInfoDto;
import com.okestro.omok.payload.dto.UserDetailsDto;
import com.okestro.omok.payload.request.RoomSaveRequestDto;
import com.okestro.omok.payload.response.RoomDetailsResponse;
import com.okestro.omok.payload.response.RoomDetailsWithUsersResponse;
import com.okestro.omok.payload.response.RoomIdResponse;
import com.okestro.omok.repository.RoomRepository;
import com.okestro.omok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public RoomIdResponse register(RoomSaveRequestDto roomSaveRequestDto) {

        Room room = roomSaveRequestDto.toRoom();
        Long userId = roomSaveRequestDto.getUserId();

        // Room 등록
        roomRepository.save(room);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_USER));

        if(user.getRoom() != null) {
            List<User> exitRoomUsers = findUsersWithRoom(user.getRoom());
            if(isRoomSizeValid(exitRoomUsers)) {
                log.info("방이 삭제 되었습니다.");
                user.getRoom().markAsDeleted();
            }
        }

        // User에 Room 할당
        user.assignRoom(room);

        // User 저장
        userRepository.save(user);

        return RoomIdResponse.toEntity(room);
    }

    private List<User> findUsersWithRoom(Room room) {
        return userRepository.findByRoom(room);
    }

    private boolean isRoomSizeValid(List<User> exitRoomsUsers) {
        return exitRoomsUsers.size() == 1;
    }


    public AttendeeInfoDto getUserInfo(Long roomId) {
        return new AttendeeInfoDto(roomRepository.findWithUserByIdAndDeletedAtIsNull(roomId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_ROOM)));
    }

    public RoomInfoDto getRoomInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_USER));

        Room room = roomRepository.findByIdAndDeletedAtIsNull(user.getRoom().getId())
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_ROOM));

        return new RoomInfoDto(room);
    }

    public RoomDetailsResponse findRoomDetails(Long roomId) {

        Room room = findRoom(roomId);

        return RoomDetailsResponse.toEntity(room);
    }

    public List<RoomDetailsWithUsersResponse> findAllRooms(Pageable pageable) {
        LocalDateTime today = LocalDateTime.now();

        List<RoomDetailsWithUsersDto> detailsWithUsers = roomRepository.findDetailsWithUsers(pageable, today);

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
        return roomRepository.findByIdAndDeletedAtIsNull(roomId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_ROOM));
    }

}