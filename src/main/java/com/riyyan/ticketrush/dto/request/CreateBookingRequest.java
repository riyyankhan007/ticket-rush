package com.riyyan.ticketrush.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateBookingRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long showId;

    @NotEmpty
    private List<Long> showSeatIds;

}