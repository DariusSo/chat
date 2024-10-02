package com.chatroom.chat.controllers;

import com.chatroom.chat.entities.User;
import com.chatroom.chat.exceptions.UserNotFoundException;
import com.chatroom.chat.services.TokenService;
import com.chatroom.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder encoder;

    public UserController(AuthenticationManager authenticationManager, TokenService tokenService, BCryptPasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user){
        userService.registerNewUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        int userId = userService.findByUsername(user.getUsername()).getId();
        if (userId == 0){
            throw new UserNotFoundException("User was not found");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token = tokenService.getToken(authentication, userId);
        return token;
    }
//    @GetMapping("/user-id")
//    public int getUserId(String token){
//        int id =
//        return tokenService.decodeUserId(token);
//    }
    @GetMapping("/byId")
    public String getById(@RequestHeader("Authorization") String token){
        long id = tokenService.decodeUserId(token);
        return userService.findById(id).getUsername();
    }


}
