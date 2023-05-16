package com.example.strongteamtesttask.repository;

import com.example.strongteamtesttask.dto.Statistics;
import com.example.strongteamtesttask.model.StatisticsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsFileRepository extends JpaRepository<StatisticsFile, Long> {

    Optional<StatisticsFile> findTopByOrderByIdDesc();

    @Query("SELECT " +
            "new com.example.strongteamtesttask.dto.Statistics(ns, count(n))" +
            "FROM News n JOIN n.newsSource ns WHERE n.newsSource.id=ns.id GROUP BY ns")
    List<Statistics> countNewsForStatistics();

}
