package com.riyyan.ticketrush.controller;

import com.riyyan.ticketrush.common.ApiResponse;
import com.riyyan.ticketrush.dto.request.CreateTheatreRequest;
import com.riyyan.ticketrush.dto.response.TheatreResponse;
import com.riyyan.ticketrush.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PreAuthorize("hasAnyRole('THEATRE_OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<TheatreResponse>> createTheatre(
            @Valid @RequestBody CreateTheatreRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<TheatreResponse>builder()
                                .success(true)
                                .message("Theatre created successfully")
                                .data(theatreService.createTheatre(request))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TheatreResponse>>> getAllTheatres() {

        return ResponseEntity.ok(
                ApiResponse.<List<TheatreResponse>>builder()
                        .success(true)
                        .message("Theatres fetched successfully")
                        .data(theatreService.getAllTheatres())
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('THEATRE_OWNER', 'ADMIN')")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<TheatreResponse>>> getMyTheatres() {

        return ResponseEntity.ok(
                ApiResponse.<List<TheatreResponse>>builder()
                        .success(true)
                        .message("Your theatres fetched successfully")
                        .data(theatreService.getMyTheatres())
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('THEATRE_OWNER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TheatreResponse>> updateTheatre(
            @PathVariable Long id,
            @Valid @RequestBody CreateTheatreRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<TheatreResponse>builder()
                        .success(true)
                        .message("Theatre updated successfully")
                        .data(theatreService.updateTheatre(id, request))
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('THEATRE_OWNER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheatre(
            @PathVariable Long id) {

        theatreService.deleteTheatre(id);

        return ResponseEntity.noContent().build();
    }
}