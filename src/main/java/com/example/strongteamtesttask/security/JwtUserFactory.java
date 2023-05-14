package com.example.strongteamtesttask.security;

import com.example.strongteamtesttask.model.Users;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(Users user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

}
