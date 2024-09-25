package com.chatroom.chat.services;

import com.chatroom.chat.entities.User;
import com.chatroom.chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final TokenService tokenService;
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(TokenService tokenService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void registerNewUser(User user){
        user.setRoles("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public User findByUsername(String username){
        return userRepository.findByUsernameU(username);
    }
}
