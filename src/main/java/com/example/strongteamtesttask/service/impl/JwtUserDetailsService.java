package com.example.strongteamtesttask.service.impl;

import com.example.strongteamtesttask.model.Users;
import com.example.strongteamtesttask.security.JwtUserFactory;
import com.example.strongteamtesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userService.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username: %s not found", username));
        }

        return JwtUserFactory.create(user);
    }
}

