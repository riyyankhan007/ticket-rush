package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.dto.request.CreateShowRequest;
import com.riyyan.ticketrush.dto.response.ShowResponse;
import com.riyyan.ticketrush.dto.response.ShowSeatResponse;

import java.util.List;

public interface ShowService {

    ShowResponse createShow(CreateShowRequest request);

    List<ShowResponse> getShowsByMovie(Long movieId);

    List<ShowSeatResponse> getSeats(Long showId);
}