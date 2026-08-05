package com.riyyan.ticketrush.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private boolean success;

    private String message;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}