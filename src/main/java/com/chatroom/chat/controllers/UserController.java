package com.chatroom.chat.controllers;

import com.chatroom.chat.entities.User;
import com.chatroom.chat.services.TokenService;
import com.chatroom.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token = tokenService.getToken(authentication, 0);
        return token;
    }

}
