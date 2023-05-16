package com.example.strongteamtesttask.controller;

import com.example.strongteamtesttask.model.StatisticsFile;
import com.example.strongteamtesttask.service.StatisticalTaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticalTaskController {

    private final StatisticalTaskService statisticalTaskService;

    public StatisticalTaskController(StatisticalTaskService statisticalTaskService) {
        this.statisticalTaskService = statisticalTaskService;
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadStatisticsFile() {

        StatisticsFile statisticsFile = statisticalTaskService.getStatisticsFile();
        byte[] fileContent = statisticsFile.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", statisticsFile.getFileName());
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

    }

}
