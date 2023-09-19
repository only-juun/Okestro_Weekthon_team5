package com.okestro.omok.repository;

import com.okestro.omok.domain.User;
import com.okestro.omok.repository.querydsl.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {
}
