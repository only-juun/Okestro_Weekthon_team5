package com.okestro.omok.repository;

import com.okestro.omok.domain.User;
import com.okestro.omok.repository.querydsl.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {

    Optional<List<User>> findAllByRoomId(Long roomId);

    Optional<User> findById(Long id);
}