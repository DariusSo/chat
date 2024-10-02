package com.chatroom.chat.services;

import com.chatroom.chat.entities.Message;
import com.chatroom.chat.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    private final TokenService tokenService;

    @Autowired
    private MessageRepository messageRepository;

    public MessageService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void saveMessage(Message message){
        message.setSentAt(LocalTime.now());
        messageRepository.save(message);
    }
    public List<Message> findByChatroom(String chatroom){
        return messageRepository.findByChatroom(chatroom);
    }
    public List<String> getChatrooms(){
        return messageRepository.getChatrooms();
    }


}
