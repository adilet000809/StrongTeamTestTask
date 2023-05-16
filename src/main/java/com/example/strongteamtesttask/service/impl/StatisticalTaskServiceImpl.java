package com.example.strongteamtesttask.service.impl;

import com.example.strongteamtesttask.dto.Statistics;
import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.model.StatisticsFile;
import com.example.strongteamtesttask.repository.StatisticsFileRepository;
import com.example.strongteamtesttask.service.StatisticalTaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StatisticalTaskServiceImpl implements StatisticalTaskService {

    private final StatisticsFileRepository statisticsFileRepository;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final CronTrigger cronTrigger;

    public StatisticalTaskServiceImpl(StatisticsFileRepository statisticsFileRepository, ThreadPoolTaskScheduler threadPoolTaskScheduler, CronTrigger cronTrigger) {
        this.statisticsFileRepository = statisticsFileRepository;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.cronTrigger = cronTrigger;
    }

    @Override
    public StatisticsFile getStatisticsFile() {
        return statisticsFileRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new EntityNotFoundException("News statistics file not found."));
    }

    @PostConstruct
    private void statisticalTask() {
        threadPoolTaskScheduler.schedule(new StatisticalTaskServiceImpl.StatisticalTask(), cronTrigger);
    }

    private class StatisticalTask implements Runnable {

        @Override
        public void run() {
            writeStatsToFile(statisticsFileRepository.countNewsForStatistics());
        }

        private void writeStatsToFile(List<Statistics> statistics) {

            StatisticsFile statisticsFile = new StatisticsFile();
            statisticsFile.setFileName("news_stats.txt");
            statisticsFile.setData(generateFileContent(statistics).getBytes());
            statisticsFileRepository.save(statisticsFile);

        }

        private String generateFileContent(List<Statistics> statistics) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
            StringBuilder contents = new StringBuilder();
            contents.append("Created at: ")
                    .append(dateFormat.format(new Date()))
                    .append("\n");
            statistics.forEach((s) ->
                    contents.append("News source name: ")
                            .append(s.getNewsSource().getName())
                            .append("; Number of news: ")
                            .append(s.getNewsCount())
                            .append("\n"));
            return contents.toString();
        }

    }

}
