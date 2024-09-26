package com.chatroom.chat.controllers;

import com.chatroom.chat.entities.Message;
import com.chatroom.chat.services.MessageService;
import com.chatroom.chat.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ChatController {
    public List<String> connectedUsers;

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, UserService userService, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.messageService = messageService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chatroom")
    public Message chatMessage(Message message) throws Exception {

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.parse(time.format(formatter));
        message.setSentAt(now);
        addUserToList(message.getUsername());

        return new Message(HtmlUtils.htmlEscape(message.getChatroom()),
                HtmlUtils.htmlEscape(message.getUsername()),
                HtmlUtils.htmlEscape(String.valueOf(message.getContent())),
                LocalDateTime.parse(HtmlUtils.htmlEscape(String.valueOf(message.getSentAt()))),
                HtmlUtils.htmlEscape(message.getType()));
    }

    @MessageMapping("/userList")
    @SendTo("/topic/usersList")
    public List<String> getChatUsersList(){
        //String json = new Gson().toJson(chatUsersList);
        return connectedUsers;
    }

    public void addUserToList(String name){
        if(!connectedUsers.contains(name)){
            connectedUsers.add(name);
        }
    }
    @MessageMapping("/leave")
    public void removeUser(String name) throws JsonProcessingException {
        String remove = null;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonName = mapper.readTree(name);
        String user = jsonName.get("name").asText();
        for(String listName : connectedUsers){
            if(listName.equals(user)){
                remove = listName;
            }
        }
        if(remove != null){
           connectedUsers.remove(remove);
        }
    }
    @MessageMapping("/messageList")
    @SendTo("/topic/messageList")
    public List<Message> getMessageList(String chatroom){
        return messageService.findByChatroom(chatroom);
    }


}
