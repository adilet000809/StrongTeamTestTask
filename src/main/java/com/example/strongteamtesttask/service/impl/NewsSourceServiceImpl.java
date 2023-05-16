package com.example.strongteamtesttask.service.impl;

import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.exception.ExistingEntityCreationException;
import com.example.strongteamtesttask.model.NewsSource;
import com.example.strongteamtesttask.repository.NewsSourceRepository;
import com.example.strongteamtesttask.service.NewsSourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsSourceServiceImpl implements NewsSourceService {

    private final NewsSourceRepository newsSourceRepository;

    public NewsSourceServiceImpl(NewsSourceRepository newsSourceRepository) {
        this.newsSourceRepository = newsSourceRepository;
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

}
