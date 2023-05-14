package com.example.strongteamtesttask.dto;

import com.example.strongteamtesttask.model.NewsSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    private NewsSource newsSource;

    private long newsCount;

}
