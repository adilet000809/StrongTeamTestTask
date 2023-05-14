package com.example.strongteamtesttask.controller;

import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.model.NewsSource;
import com.example.strongteamtesttask.service.NewsSourceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news-sources")
public class NewsSourceController {

    private final NewsSourceService newsSourceService;

    public NewsSourceController(NewsSourceService newsSourceService) {
        this.newsSourceService = newsSourceService;
    }

    @GetMapping
    public List<NewsSource> getAllNewsSources() {
        return newsSourceService.getAllNewsSources();
    }

    @GetMapping("/{id}")
    public NewsSource getNewsSourceById(@PathVariable Long id) throws EntityNotFoundException {
        return newsSourceService.getNewsSourceById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsSource addNewsSource(@Valid @RequestBody NewsSource newsSource) {
        return newsSourceService.addNewsSource(newsSource);
    }

    @PutMapping("/{id}")
    public NewsSource updateNewsSource(@PathVariable Long id, @Valid  @RequestBody NewsSource newsSource)
            throws EntityNotFoundException {
        return newsSourceService.updateNewsSource(id, newsSource);
    }

    @DeleteMapping("/{id}")
    public void deleteNewsSource(@PathVariable Long id) throws EntityNotFoundException {
        newsSourceService.deleteNewsSource(id);
    }

}
