package com.okestro.omok.service;

import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.payload.response.UserDetailsResponse.UserNameResponse;
import com.okestro.omok.repository.RoomRepository;
import com.okestro.omok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    public UserNameResponse findUserName(Long userId) {
        return UserNameResponse.toEntity(findUser(userId));
    }

    @Transactional
    public void participationRoom(Long userId, Long participationRoomId) {

        User user = findUser(userId);

        isSameRoomAsUser(user,participationRoomId);

        Room participationRoom = findParticipationRoom(participationRoomId);

        List<User> exitRoomUsers = findUsersWithRoom(user.getRoom());

        if(isRoomSizeValid(user, exitRoomUsers)) {
            log.info("방이 삭제 되었습니다.");
            user.getRoom().setDeletedAt();
        }

        user.setRoom(participationRoom);
    }

    private Room findParticipationRoom(Long participationRoomId) {
        Room participationRoom = findByDeletedAtIsNullRoom(participationRoomId);
        List<User> participationRooms = findUsersWithRoom(participationRoom);

        validateRoomCapacity(participationRooms.size(), participationRoom.getLimitedAttendees());

        return participationRoom;
    }

    private boolean isRoomSizeValid(User user, List<User> exitRoomsUsers) {
        return user.getRoom() != null && exitRoomsUsers.size() == 1;
    }

    private void isSameRoomAsUser(User user, Long participationRoomId) {
        if (user.getRoom() != null && user.getRoom().getId().equals(participationRoomId)) {
            log.info("이미 방에 참여 중입니다.");
            throw new ClientException(ErrorCode.ALREADY_PARTICIPATION_ROOM);
        }
    }

    private void validateRoomCapacity(Integer currentParticipationRoomUser, Integer limitedAttendees) {
        if(limitedAttendees <= currentParticipationRoomUser) {
            log.info("방 인원이 초과되었습니다.");
            throw new ClientException(ErrorCode.EXCEED_PARTICIPATION_ROOM);
        }
    }

    private List<User> findUsersWithRoom(Room room) {
        return userRepository.findByRoom(room);
    }

    private Room findByDeletedAtIsNullRoom(Long roomId) {
        return roomRepository.findByIdAndDeletedAtIsNull(roomId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_ROOM));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_USER));
    }
}
