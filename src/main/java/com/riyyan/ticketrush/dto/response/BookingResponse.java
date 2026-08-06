package com.riyyan.ticketrush.dto.response;

import com.riyyan.ticketrush.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BookingResponse {

    private Long bookingId;

    private BookingStatus status;

    private BigDecimal totalAmount;

    private List<String> seats;

    private LocalDateTime createdAt;

}