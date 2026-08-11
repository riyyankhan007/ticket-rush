package com.riyyan.ticketrush.dto.response;

import com.riyyan.ticketrush.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MyBookingResponse {

    private Long bookingId;

    private String movie;

    private String theatre;

    private String screen;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<String> seats;

    private BigDecimal totalAmount;

    private BookingStatus status;

    private LocalDateTime createdAt;
}