package com.example.strongteamtesttask.service;

import com.example.strongteamtesttask.model.Topic;

import java.util.List;

public interface TopicService {

    Topic addTopic(Topic topic);

    Topic updateTopic(Long id, Topic topic);

    void deleteTopic(Long id);

    Topic getTopicById(Long id);

    List<Topic> getAllTopics();

}
