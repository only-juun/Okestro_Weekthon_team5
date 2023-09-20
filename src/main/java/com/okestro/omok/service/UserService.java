package com.okestro.omok.service;

import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.repository.RoomRepository;
import com.okestro.omok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public void participationRoom(Long userId, Long roomId) {

        User user = findUser(userId);

        if(isUserParticipation(user.getRoom())) {
            throw new ClientException(ErrorCode.ALREADY_PARTICIPATION_ROOM);
        }

        Room room = findRoom(roomId);

        List<User> rooms = findUsersWithRoom(room);

        if(isCurrentParticipant(rooms.size(), room.getLimitedAttendees())) {
            user.setRoom(room);
            return;
        }

        throw new ClientException(ErrorCode.EXCEED_PARTICIPATION_ROOM);
    }

    private boolean isCurrentParticipant(Integer current, Integer limitedAttendees) {
        return current < limitedAttendees;
    }

    private List<User> findUsersWithRoom(Room room) {
        return userRepository.findByRoom(room);
    }

    private boolean isUserParticipation(Room room) {
        return room != null;
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_ROOM));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_USER));
    }
}
