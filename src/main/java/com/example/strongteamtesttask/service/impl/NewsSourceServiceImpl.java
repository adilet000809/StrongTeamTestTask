package com.example.strongteamtesttask.service.impl;

import com.example.strongteamtesttask.dto.Statistics;
import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.exception.ExistingEntityCreationException;
import com.example.strongteamtesttask.model.NewsSource;
import com.example.strongteamtesttask.repository.NewsSourceRepository;
import com.example.strongteamtesttask.service.NewsSourceService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewsSourceServiceImpl implements NewsSourceService {

    private final NewsSourceRepository newsSourceRepository;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final CronTrigger cronTrigger;

    public NewsSourceServiceImpl(NewsSourceRepository newsSourceRepository, ThreadPoolTaskScheduler threadPoolTaskScheduler,
                                 CronTrigger cronTrigger) {
        this.newsSourceRepository = newsSourceRepository;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.cronTrigger = cronTrigger;
    }

    @Override
    public NewsSource addNewsSource(NewsSource newsSource) {
        // The 'save' method automatically updates the record if id is provided
        // Prevention of updating an existing record instead of creation.
        if (newsSource.getId() != null && newsSourceRepository.existsById(newsSource.getId())) {
            throw new ExistingEntityCreationException(String.format("News source with id %d already exists", newsSource.getId()));
        }
        return newsSourceRepository.save(newsSource);
    }

    @Override
    public NewsSource updateNewsSource(Long id, NewsSource newsSource) {
        NewsSource oldNewsSource = newsSourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("News source with id: %d not found.", id)));
        // Updating oldNewsSource contents
        oldNewsSource.setName(newsSource.getName());
        return newsSourceRepository.save(oldNewsSource);
    }

    @Override
    public void deleteNewsSource(Long id) {
        newsSourceRepository.deleteById(id);
    }

    @Override
    public NewsSource getNewsSourceById(Long id) {
        return newsSourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("News source with id: %d not found.", id)));
    }

    @Override
    public List<NewsSource> getAllNewsSources() {
        return newsSourceRepository.findAll();
    }

    @PostConstruct
    private void statisticalTask() {
        threadPoolTaskScheduler.schedule(new StatisticalTask(), cronTrigger);
    }

    private class StatisticalTask implements Runnable {

        @Override
        public void run() {
            writeStatsToFile(newsSourceRepository.countNewsForStatistics());
        }

        private void writeStatsToFile(List<Statistics> statistics) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
            String fileLocation = String.format("news_stats_%s.txt", dateFormat.format(new Date()));
            File file = new File(new File("stats/"), fileLocation);
            try (Writer writer = new BufferedWriter(new FileWriter(file))) {
                StringBuilder contents = new StringBuilder();
                statistics.forEach((s) ->
                        contents.append("News source name: ")
                                .append(s.getNewsSource().getName())
                                .append("; Number of news: ")
                                .append(s.getNewsCount())
                                .append("\n"));
                writer.write(contents.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
