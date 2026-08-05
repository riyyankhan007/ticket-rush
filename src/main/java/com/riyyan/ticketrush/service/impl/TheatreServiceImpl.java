package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.dto.request.CreateTheatreRequest;
import com.riyyan.ticketrush.dto.response.TheatreResponse;
import com.riyyan.ticketrush.entity.Theatre;
import com.riyyan.ticketrush.mapper.TheatreMapper;
import com.riyyan.ticketrush.repository.TheatreRepository;
import com.riyyan.ticketrush.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    @Override
    public TheatreResponse createTheatre(CreateTheatreRequest request) {

        Theatre theatre = TheatreMapper.toEntity(request);

        return TheatreMapper.toResponse(
                theatreRepository.save(theatre)
        );
    }

    @Override
    public List<TheatreResponse> getAllTheatres() {

        return theatreRepository.findAll()
                .stream()
                .map(TheatreMapper::toResponse)
                .toList();
    }
}