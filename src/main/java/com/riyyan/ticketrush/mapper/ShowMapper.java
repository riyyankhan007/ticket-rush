package com.riyyan.ticketrush.mapper;

import com.riyyan.ticketrush.dto.response.ShowResponse;
import com.riyyan.ticketrush.dto.response.ShowSeatResponse;
import com.riyyan.ticketrush.entity.Show;
import com.riyyan.ticketrush.entity.ShowSeat;

import java.math.BigDecimal;

public class ShowMapper {

    public static ShowResponse toResponse(Show show, BigDecimal price) {

        return ShowResponse.builder()
                .id(show.getId())
                .movie(show.getMovie().getTitle())
                .screen(show.getScreen().getName())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .price(price)
                .build();
    }

    public static ShowSeatResponse toSeatResponse(ShowSeat showSeat) {

        return ShowSeatResponse.builder()
                .id(showSeat.getId())
                .seat(showSeat.getSeat().getRowName() + showSeat.getSeat().getSeatNumber())
                .status(showSeat.getStatus())
                .price(showSeat.getPrice())
                .build();
    }
}