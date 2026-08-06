package com.riyyan.ticketrush.dto.response;

import com.riyyan.ticketrush.enums.SeatStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ShowSeatResponse {

    private Long id;

    private String seat;

    private SeatStatus status;

    private BigDecimal price;
}