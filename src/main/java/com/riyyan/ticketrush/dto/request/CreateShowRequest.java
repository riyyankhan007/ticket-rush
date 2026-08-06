package com.riyyan.ticketrush.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateShowRequest {

    @NotNull
    private Long movieId;

    @NotNull
    private Long screenId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private BigDecimal price;
}