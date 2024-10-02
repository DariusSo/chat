package com.chatroom.chat.repositories;

import com.chatroom.chat.entities.Message;
import com.chatroom.chat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
    User findByUsernameU(String username);

    @Query(value = "SELECT * FROM users WHERE id = ?1", nativeQuery = true)
    User findById(long id);
}
