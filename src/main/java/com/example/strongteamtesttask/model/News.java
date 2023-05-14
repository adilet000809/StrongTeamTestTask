package com.example.strongteamtesttask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "News title cannot be empty")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "News content cannot be empty")
    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "date_of_publish")
    private Date dateOfPublish = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "news_source_id")
    private NewsSource newsSource;

    @ManyToMany
    @JoinTable(name = "news_topics",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topics = new HashSet<>();

}
