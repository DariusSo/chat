package com.chatroom.chat.repositories;

import com.chatroom.chat.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
