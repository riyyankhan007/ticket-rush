package com.riyyan.ticketrush.mapper;

import com.riyyan.ticketrush.dto.request.CreateTheatreRequest;
import com.riyyan.ticketrush.dto.response.TheatreResponse;
import com.riyyan.ticketrush.entity.Theatre;

public class TheatreMapper {

    public static Theatre toEntity(CreateTheatreRequest request) {

        Theatre theatre = new Theatre();

        theatre.setName(request.getName());
        theatre.setCity(request.getCity());
        theatre.setAddress(request.getAddress());

        return theatre;
    }

    public static TheatreResponse toResponse(Theatre theatre) {

        return TheatreResponse.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .city(theatre.getCity())
                .address(theatre.getAddress())
                .createdAt(theatre.getCreatedAt())
                .build();
    }
}