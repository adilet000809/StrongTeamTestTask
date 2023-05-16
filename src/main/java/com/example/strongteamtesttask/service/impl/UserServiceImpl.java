package com.example.strongteamtesttask.service.impl;

import com.example.strongteamtesttask.dto.AuthRequest;
import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.exception.ExistingEntityCreationException;
import com.example.strongteamtesttask.model.Users;
import com.example.strongteamtesttask.repository.UserRepository;
import com.example.strongteamtesttask.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users addUser(AuthRequest authRequest) {
        if (userRepository.existsByUsername(authRequest.getUsername())) {
            throw new ExistingEntityCreationException(String.format("User with username: %s already exists.", authRequest.getUsername()));
        }
        Users user = new Users();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Users findByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with username: %s not found.", username)));
    }

    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
