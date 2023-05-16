package com.example.strongteamtesttask.service;

import com.example.strongteamtesttask.dto.AuthRequest;
import com.example.strongteamtesttask.model.Users;

public interface UserService {

    Users addUser(AuthRequest authRequest);

    Users findByUserName(String username);

    boolean existByUsername(String username);

}
