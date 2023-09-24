package com.okestro.omok.repository.querydsl;

import com.okestro.omok.payload.dto.RoomDetailsWithUsersDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepositoryCustom {

    List<RoomDetailsWithUsersDto> findDetailsWithUsers(Pageable pageable, LocalDateTime today);
}
