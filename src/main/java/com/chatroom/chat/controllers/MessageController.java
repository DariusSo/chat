package com.chatroom.chat.controllers;

import com.chatroom.chat.entities.Message;
import com.chatroom.chat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @CrossOrigin
    @GetMapping
    public List<String> getChatrooms(){
        return messageService.getChatrooms();
    }
    @CrossOrigin
    @PostMapping
    public void saveMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
    }
    @CrossOrigin
    @GetMapping("/chatroom")
    public List<Message> findByChatroom(String chatroom){
        return messageService.findByChatroom(chatroom);
    }
}
