package com.riyyan.ticketrush.controller;

import com.riyyan.ticketrush.common.ApiResponse;
import com.riyyan.ticketrush.dto.request.CreateBookingRequest;
import com.riyyan.ticketrush.dto.response.BookingResponse;
import com.riyyan.ticketrush.dto.response.MyBookingResponse;
import com.riyyan.ticketrush.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

    private final BookingService bookingService;

    @Operation(
            summary = "Create a booking",
            description = """
                    Creates a confirmed movie ticket booking for the
                    authenticated user.
                    
                    The selected show seats are validated, the total
                    booking amount is calculated, the seats are reserved,
                    and the booking is persisted.
                    
                    The authenticated user is determined from the JWT token.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Booking created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid booking request or unavailable seats"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> bookSeats(
            @Valid @RequestBody CreateBookingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<BookingResponse>builder()
                                .success(true)
                                .message("Booking created successfully")
                                .data(
                                        bookingService.bookSeats(request)
                                )
                                .build()
                );
    }

    @Operation(
            summary = "Get my bookings",
            description = """
                    Returns all bookings belonging to the currently
                    authenticated user.
                    
                    Bookings are returned with the newest booking first.
                    
                    The user is identified from the JWT token, so a user
                    cannot retrieve another user's booking history.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Bookings fetched successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<MyBookingResponse>>> getMyBookings() {

        return ResponseEntity.ok(
                ApiResponse.<List<MyBookingResponse>>builder()
                        .success(true)
                        .message("Bookings fetched successfully")
                        .data(bookingService.getMyBookings())
                        .build()
        );
    }

    @Operation(
            summary = "Get my booking by ID",
            description = """
                    Returns detailed information about a specific booking
                    belonging to the authenticated user.
                    
                    The booking includes movie, theatre, screen, showtime,
                    selected seats, total amount and booking status.
                    
                    A booking belonging to another user cannot be accessed.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Booking fetched successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Booking not found"
            )
    })
    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<MyBookingResponse>> getMyBooking(
            @Parameter(
                    description = "ID of the booking",
                    example = "5"
            )
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                ApiResponse.<MyBookingResponse>builder()
                        .success(true)
                        .message("Booking fetched successfully")
                        .data(bookingService.getMyBooking(bookingId))
                        .build()
        );
    }
}