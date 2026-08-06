package com.riyyan.ticketrush.controller;

import com.riyyan.ticketrush.common.ApiResponse;
import com.riyyan.ticketrush.dto.request.CreateBookingRequest;
import com.riyyan.ticketrush.dto.response.BookingResponse;
import com.riyyan.ticketrush.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> bookSeats(
            @Valid @RequestBody CreateBookingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<BookingResponse>builder()
                                .success(true)
                                .message("Booking created successfully")
                                .data(bookingService.bookSeats(request))
                                .build()
                );
    }
}