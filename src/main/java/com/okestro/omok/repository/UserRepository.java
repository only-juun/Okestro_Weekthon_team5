package com.okestro.omok.repository;

import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.repository.querydsl.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {

    Optional<List<User>> findAllByRoomId(Long roomId);

    Optional<User> findById(Long id);

    List<User> findByRoom(Room room);
}