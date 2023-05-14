package com.example.strongteamtesttask.repository;

import com.example.strongteamtesttask.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAll();
    @Override
    Optional<Topic> findById(Long id);

}
