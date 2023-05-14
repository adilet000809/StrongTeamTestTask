package com.example.strongteamtesttask.repository;

import com.example.strongteamtesttask.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsername(String userName);

    boolean existsByUsername(String userName);

}
