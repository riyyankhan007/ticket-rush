package com.riyyan.ticketrush.controller;

import com.riyyan.ticketrush.common.ApiResponse;
import com.riyyan.ticketrush.dto.request.CreateScreenRequest;
import com.riyyan.ticketrush.dto.response.ScreenResponse;
import com.riyyan.ticketrush.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScreenResponse>> createScreen(
            @Valid @RequestBody CreateScreenRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<ScreenResponse>builder()
                                .success(true)
                                .message("Screen created successfully")
                                .data(screenService.createScreen(request))
                                .build()
                );
    }
}