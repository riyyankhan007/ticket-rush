package com.riyyan.ticketrush.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ShowResponse {

    private Long id;

    private String movie;

    private String screen;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal price;
}