package com.example.strongteamtesttask.repository;

import com.example.strongteamtesttask.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Override
    Optional<News> findById(Long id);

    Page<News> findAll(Pageable pageable);

    Page<News> findByNewsSourceId(Long newsSourceId, Pageable pageable);

    Page<News> findByTopicsId(Long topicId, Pageable pageable);

}
