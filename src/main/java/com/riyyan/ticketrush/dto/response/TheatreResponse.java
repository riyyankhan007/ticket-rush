package com.riyyan.ticketrush.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TheatreResponse {

    private Long id;

    private String name;

    private String city;

    private String address;

    private LocalDateTime createdAt;
}