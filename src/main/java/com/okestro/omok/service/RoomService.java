package com.okestro.omok.service;


import com.okestro.omok.domain.Room;
import com.okestro.omok.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public void register(Room room) {
        roomRepository.save(room);
    }
}
