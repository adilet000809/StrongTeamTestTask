package com.example.strongteamtesttask.controller;

import com.example.strongteamtesttask.exception.EntityNotFoundException;
import com.example.strongteamtesttask.model.Topic;
import com.example.strongteamtesttask.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/{id}")
    public Topic getTopicById(@PathVariable Long id) throws EntityNotFoundException {
        return topicService.getTopicById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Topic addTopic(@Valid @RequestBody Topic topic) {
        return topicService.addTopic(topic);
    }

    @PutMapping("/{id}")
    public Topic updateTopic(@PathVariable Long id, @Valid  @RequestBody Topic topic)
            throws EntityNotFoundException {
        return topicService.updateTopic(id, topic);
    }

    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable Long id) throws EntityNotFoundException {
        topicService.deleteTopic(id);
    }

}
