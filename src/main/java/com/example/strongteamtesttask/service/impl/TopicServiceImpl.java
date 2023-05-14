package com.example.strongteamtesttask.service.impl;

import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.exception.ExistingEntityCreationException;
import com.example.strongteamtesttask.model.Topic;
import com.example.strongteamtesttask.repository.TopicRepository;
import com.example.strongteamtesttask.service.TopicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic addTopic(Topic topic) {
        // The 'save' method automatically updates the record if id is provided
        // Prevention of updating an existing record instead of creation.
        if (topic.getId() != null && topicRepository.existsById(topic.getId())) {
            throw new ExistingEntityCreationException(String.format("Topic with id: %d already exists.", topic.getId()));
        }
        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(Long id, Topic topic) {
        Topic oldTopic = topicRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("Topic with id: %d not found.", id)));
        // Updating oldTopic contents
        oldTopic.setName(topic.getName());
        return topicRepository.save(oldTopic);
    }

    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("Topic with id: %d not found.", id)));
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

}
