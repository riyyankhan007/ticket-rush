package com.riyyan.ticketrush.controller;

import com.riyyan.ticketrush.common.ApiResponse;
import com.riyyan.ticketrush.dto.request.CreateShowRequest;
import com.riyyan.ticketrush.dto.response.ShowResponse;
import com.riyyan.ticketrush.dto.response.ShowSeatResponse;
import com.riyyan.ticketrush.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PreAuthorize("hasAnyRole('THEATRE_OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<ShowResponse>> createShow(
            @Valid @RequestBody CreateShowRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<ShowResponse>builder()
                                .success(true)
                                .message("Show created successfully")
                                .data(showService.createShow(request))
                                .build()
                );
    }

    @GetMapping("/{showId}/seats")
    public ResponseEntity<ApiResponse<List<ShowSeatResponse>>> getSeats(
            @PathVariable Long showId) {

        return ResponseEntity.ok(
                ApiResponse.<List<ShowSeatResponse>>builder()
                        .success(true)
                        .message("Seats fetched successfully")
                        .data(showService.getSeats(showId))
                        .build()
        );
    }
}