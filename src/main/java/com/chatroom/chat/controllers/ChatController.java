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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatController {
    public List<String> connectedUsers = new ArrayList<>();

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

        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime now = LocalTime.parse(time.format(formatter));
        message.setSentAt(now);
        messageService.saveMessage(message);
        addUserToList(message.getUsername(), message.getChatroom());


        return new Message(HtmlUtils.htmlEscape(message.getChatroom()),
                HtmlUtils.htmlEscape(message.getUsername()),
                HtmlUtils.htmlEscape(String.valueOf(message.getContent())),
                LocalTime.parse(HtmlUtils.htmlEscape(String.valueOf(message.getSentAt()))),
                HtmlUtils.htmlEscape(message.getType()));
    }

    @MessageMapping("/userList")
    @SendTo("/topic/usersList")
    public List<String> getChatUsersList(){
        //String json = new Gson().toJson(chatUsersList);
//        Map<String, String> connectedToChatroom = new HashMap<>();
//        for(Map.Entry<String, String> entry : connectedUsers.entrySet()){
//            if(entry.getValue().equals(chatroom)){
//                connectedToChatroom.put(entry.getKey(), entry.getValue());
//            }
//        }
        return connectedUsers;
    }

    public void addUserToList(String name, String chatroom){
        if(!connectedUsers.contains(name)){
            connectedUsers.add(name);
        }
//        connectedUsers.put(name, chatroom);
    }
    @MessageMapping("/leave")
    @SendTo("/topic/usersList")
    public List<String> removeUser(String name) throws JsonProcessingException {
        String remove = null;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonName = mapper.readTree(name);
        String user = jsonName.get("username").asText();
        connectedUsers.remove(name);
//        for(String listName : connectedUsers){
//            if(listName.equals(user)){
//                remove = listName;
//            }
//        }
//        if(remove != null){
//           connectedUsers.remove(remove);
//        }
//        connectedUsers.remove(name);
//        Map<String, String> connectedToChatroom = new HashMap<>();
//        for(Map.Entry<String, String> entry : connectedUsers.entrySet()){
//            if(entry.getValue().equals(chatroom)){
//                connectedToChatroom.put(entry.getKey(), entry.getValue());
//            }
//        }
        return connectedUsers;
    }
    @MessageMapping("/messageList")
    @SendTo("/topic/messageList")
    public List<Message> getMessageList(String chatroom){
        return messageService.findByChatroom(chatroom);
    }


}
