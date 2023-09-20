package com.okestro.omok.service;


import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.payload.dto.AttendeeInfoDto;
import com.okestro.omok.payload.dto.RoomInfoDto;
import com.okestro.omok.repository.RoomRepository;
import com.okestro.omok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
        user.assignRoom(room);

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
}
