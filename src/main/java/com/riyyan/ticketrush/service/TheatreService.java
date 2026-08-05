package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.dto.request.CreateTheatreRequest;
import com.riyyan.ticketrush.dto.response.TheatreResponse;

import java.util.List;

public interface TheatreService {

    TheatreResponse createTheatre(CreateTheatreRequest request);

    List<TheatreResponse> getAllTheatres();
}