package com.example.strongteamtesttask.service;

import com.example.strongteamtesttask.dto.NewsRequest;
import com.example.strongteamtesttask.model.News;
import org.springframework.data.domain.Page;

public interface NewsService {

    News addNews(NewsRequest newsRequest);

    News updateNews(Long id, NewsRequest newsRequest);

    void deleteNews(Long id);

    News getNewsById(Long id);

    Page<News> getAllNews(int page, int size);

    Page<News> getNewsByNewsSourceId(Long newsSourceId, int page, int size);

    Page<News> getNewsByTopicId(Long topicId, int page, int size);

}
