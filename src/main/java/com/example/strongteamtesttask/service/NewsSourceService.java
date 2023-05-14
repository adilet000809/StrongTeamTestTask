package com.example.strongteamtesttask.service;

import com.example.strongteamtesttask.model.NewsSource;

import java.util.List;

public interface NewsSourceService {

    NewsSource addNewsSource(NewsSource newsSource);

    NewsSource updateNewsSource(Long id, NewsSource newsSource);

    void deleteNewsSource(Long id);

    NewsSource getNewsSourceById(Long id);

    List<NewsSource> getAllNewsSources();

}
