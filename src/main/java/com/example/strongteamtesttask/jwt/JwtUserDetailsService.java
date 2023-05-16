package com.example.strongteamtesttask.jwt;

import com.example.strongteamtesttask.model.Users;
import com.example.strongteamtesttask.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public JwtUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = repository.findByUsername(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("user not found " + username);
        return new JwtUserDetails(user.get().getId(), user.get().getUsername(), user.get().getPassword());
    }

}
