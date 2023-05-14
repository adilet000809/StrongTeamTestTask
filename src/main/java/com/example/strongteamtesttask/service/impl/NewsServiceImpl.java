package com.example.strongteamtesttask.service.impl;

import com.example.strongteamtesttask.dto.NewsRequest;
import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.model.News;
import com.example.strongteamtesttask.model.NewsSource;
import com.example.strongteamtesttask.model.Topic;
import com.example.strongteamtesttask.repository.NewsRepository;
import com.example.strongteamtesttask.repository.NewsSourceRepository;
import com.example.strongteamtesttask.repository.TopicRepository;
import com.example.strongteamtesttask.service.NewsService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsSourceRepository newsSourceRepository;
    private final TopicRepository topicRepository;

    public NewsServiceImpl(NewsRepository newsRepository, NewsSourceRepository newsSourceRepository,
                           TopicRepository topicRepository) {
        this.newsRepository = newsRepository;
        this.newsSourceRepository = newsSourceRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    @Transactional
    public News addNews(NewsRequest newsRequest) {
        News news = new News();
        news.setTitle(newsRequest.getTitle());
        news.setContent(newsRequest.getContent());
        assignNewsSourceAndNewsTopic(newsRequest, news);
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public News updateNews(Long id, NewsRequest newsRequest) {
        News oldNews = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("News with id: %d not found.", id)));
        // Updating oldNews contents
        oldNews.setTitle(newsRequest.getTitle());
        oldNews.setContent(newsRequest.getContent());
        assignNewsSourceAndNewsTopic(newsRequest, oldNews);
        return newsRepository.save(oldNews);
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("News with id: %d not found.", id)));
    }

    @Override
    public Page<News> getAllNews(@Min(0) int page, @Min(1) int size) {
        // Page - 1 to start page counting from 1 not 0
        if (page > 0) {
            page--;
        }
        return newsRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<News> getNewsByNewsSourceId(Long newsSourceId, @Min(0) int page, @Min(1) int size) {
        // Page - 1 to start page counting from 1 not 0
        if (page > 0) {
            page--;
        }
        return newsRepository.findByNewsSourceId(newsSourceId, PageRequest.of(page, size));
    }

    @Override
    public Page<News> getNewsByTopicId(Long topicId, int page, int size) {
        // Page - 1 to start page counting from 1 not 0
        if (page > 0) {
            page--;
        }
        return newsRepository.findByTopicsId(topicId, PageRequest.of(page, size));
    }

    private void assignNewsSourceAndNewsTopic(NewsRequest newsRequest, News news) {
        NewsSource newsSource = newsSourceRepository.findById(newsRequest.getNewsSourceId())
                .orElseThrow(() -> new EntityNotFoundException("News source not found"));
        news.setNewsSource(newsSource);
        Set<Topic> topics = newsRequest.getTopicId().stream()
                .map(topic -> topicRepository.findById(topic)
                        .orElseThrow(() -> new EntityNotFoundException("Topic not found")))
                .collect(Collectors.toSet());
        news.setTopics(topics);
    }

}
