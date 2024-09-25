package com.chatroom.chat.repositories;

import com.chatroom.chat.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT * FROM messages WHERE chatroom = ?1", nativeQuery = true)
    List<Message> findByChatroom(String chatroom);

    @Query(value = "SELECT DISTINCT chatroom FROM messages", nativeQuery = true)
    List<String> getChatrooms();
}
