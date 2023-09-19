package com.okestro.omok.repository;

import com.okestro.omok.domain.Room;
import com.okestro.omok.repository.querydsl.RoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long>, RoomRepositoryCustom {
}
