package com.chatroom.chat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="chatroom")
    private String chatroom;
    @Column(name="username")
    private String username;
    @Column(name="content")
    private String content;
    @Column(name="sent_at")
    private LocalDateTime sentAt;
    @Column(name="type")
    private String type;

    public Message(String chatroom, String username, String content, LocalDateTime sentAt, String type) {
        this.chatroom = chatroom;
        this.username = username;
        this.content = content;
        this.sentAt = sentAt;
        this.type = type;
    }
}
