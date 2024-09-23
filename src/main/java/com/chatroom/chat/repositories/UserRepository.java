package com.chatroom.chat.repositories;

import com.chatroom.chat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
