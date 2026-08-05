package com.riyyan.ticketrush.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MovieResponse {

    private Long id;

    private String title;

    private String language;

    private Integer duration;

    private String genre;

    private LocalDateTime createdAt;
}