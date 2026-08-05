package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.dto.request.CreateScreenRequest;
import com.riyyan.ticketrush.dto.response.ScreenResponse;

public interface ScreenService {

    ScreenResponse createScreen(CreateScreenRequest request);

}