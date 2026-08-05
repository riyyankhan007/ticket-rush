package com.riyyan.ticketrush.mapper;

import com.riyyan.ticketrush.dto.response.ScreenResponse;
import com.riyyan.ticketrush.entity.Screen;

public class ScreenMapper {

    public static ScreenResponse toResponse(Screen screen){

        return ScreenResponse.builder()
                .id(screen.getId())
                .name(screen.getName())
                .totalSeats(screen.getTotalSeats())
                .build();
    }

}