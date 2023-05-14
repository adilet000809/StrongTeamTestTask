package com.example.strongteamtesttask.controller;

import com.example.strongteamtesttask.dto.NewsRequest;
import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.model.News;
import com.example.strongteamtesttask.service.NewsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final static String PAGE_NUMBER_MIN_VALUE_ERROR_MESSAGE = "Page number must not be less than one";
    private final static String PAGE_SIZE_MIN_VALUE_ERROR_MESSAGE = "Page size must not be less than one";

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{id}")
    public News getNewsById(@PathVariable Long id) throws EntityNotFoundException {
        return newsService.getNewsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public News addNews(@Valid @RequestBody NewsRequest newsRequest) {
        return newsService.addNews(newsRequest);
    }

    @PutMapping("/{id}")
    public News updateNews(@PathVariable Long id, @Valid @RequestBody NewsRequest newsRequest)
            throws EntityNotFoundException {
        return newsService.updateNews(id, newsRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) throws EntityNotFoundException {
        newsService.deleteNews(id);
    }

    @GetMapping
    public List<News> getAllNews(@RequestParam(defaultValue = "0") @Min(value = 0, message = PAGE_NUMBER_MIN_VALUE_ERROR_MESSAGE) int page,
                                 @RequestParam(defaultValue = "10") @Min(value = 1, message = PAGE_SIZE_MIN_VALUE_ERROR_MESSAGE) int size) {
        Page<News> newsPage = newsService.getAllNews(page, size);
        return newsPage.getContent();
    }

    @GetMapping("/sources/{sourceId}")
    public List<News> getNewsBySourceId(@PathVariable Long sourceId,
                                        @RequestParam(defaultValue = "0") @Min(value = 0, message = PAGE_NUMBER_MIN_VALUE_ERROR_MESSAGE) int page,
                                        @RequestParam(defaultValue = "10") @Min(value = 1, message = PAGE_SIZE_MIN_VALUE_ERROR_MESSAGE) int size) {
        Page<News> newsPage = newsService.getNewsByNewsSourceId(sourceId, page, size);
        return newsPage.getContent();
    }

    @GetMapping("/topics/{topicId}")
    public List<News> getNewsByTopicId(@PathVariable Long topicId,
                                        @RequestParam(defaultValue = "0") @Min(value = 0, message = PAGE_NUMBER_MIN_VALUE_ERROR_MESSAGE) int page,
                                        @RequestParam(defaultValue = "10") @Min(value = 1, message = PAGE_SIZE_MIN_VALUE_ERROR_MESSAGE) int size) {
        Page<News> newsPage = newsService.getNewsByTopicId(topicId, page, size);
        return newsPage.getContent();
    }

}
