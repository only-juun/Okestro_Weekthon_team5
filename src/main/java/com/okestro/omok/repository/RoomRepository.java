package com.okestro.omok.repository;

import com.okestro.omok.domain.Room;
import com.okestro.omok.repository.querydsl.RoomRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {

    @EntityGraph(attributePaths = {"users"})
    Optional<Room> findWithUserByIdAndDeletedAtIsNull(Long roomId);

    Optional<Room> findByIdAndDeletedAtIsNull(Long id);
}
