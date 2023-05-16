package com.example.strongteamtesttask.repository;

import com.example.strongteamtesttask.model.NewsSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {

    List<NewsSource> findAll();
    @Override
    Optional<NewsSource> findById(Long id);

}
