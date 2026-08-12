package com.riyyan.ticketrush.controller;

import com.riyyan.ticketrush.common.ApiResponse;
import com.riyyan.ticketrush.dto.request.CreateShowRequest;
import com.riyyan.ticketrush.dto.response.ShowResponse;
import com.riyyan.ticketrush.dto.response.ShowSeatResponse;
import com.riyyan.ticketrush.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Create a show",
            description = """
                    Creates a new movie show for a screen.
                    
                    The show is associated with a movie and screen,
                    and show seats are automatically created from the
                    seats configured for that screen.
                    
                    Only THEATRE_OWNER and ADMIN users can create shows.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Show created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid show details"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "User does not have permission to create shows"
            )
    })
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

    @Operation(
            summary = "Get shows for a movie",
            description = """
                    Returns all scheduled shows for the specified movie.
                    
                    Shows are ordered by their start time.
                    
                    Customers use this endpoint to select a showtime
                    before proceeding to seat selection.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Shows fetched successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Movie not found"
            )
    })
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ShowResponse>>> getShowsByMovie(
            @Parameter(
                    description = "ID of the movie",
                    example = "2"
            )
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                ApiResponse.<List<ShowResponse>>builder()
                        .success(true)
                        .message("Shows fetched successfully")
                        .data(showService.getShowsByMovie(movieId))
                        .build()
        );
    }

    @Operation(
            summary = "Get seats for a show",
            description = """
                    Returns all seats available for a specific show.
                    
                    Each seat includes its seat information, current
                    booking status and price.
                    
                    Customers use this endpoint after selecting a
                    showtime to display the seat selection screen.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Show seats fetched successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Show not found"
            )
    })
    @GetMapping("/{showId}/seats")
    public ResponseEntity<ApiResponse<List<ShowSeatResponse>>> getSeats(
            @Parameter(
                    description = "ID of the show",
                    example = "2"
            )
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