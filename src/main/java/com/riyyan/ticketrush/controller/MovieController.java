package com.riyyan.ticketrush.controller;

import com.riyyan.ticketrush.common.ApiResponse;
import com.riyyan.ticketrush.dto.request.CreateMovieRequest;
import com.riyyan.ticketrush.dto.response.MovieResponse;
import com.riyyan.ticketrush.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
            @Valid @RequestBody CreateMovieRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<MovieResponse>builder()
                                .success(true)
                                .message("Movie created successfully")
                                .data(movieService.createMovie(request))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies() {

        return ResponseEntity.ok(
                ApiResponse.<List<MovieResponse>>builder()
                        .success(true)
                        .message("Movies fetched successfully")
                        .data(movieService.getAllMovies())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovie(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<MovieResponse>builder()
                        .success(true)
                        .message("Movie fetched successfully")
                        .data(movieService.getMovieById(id))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(
            @PathVariable Long id) {

        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }
}