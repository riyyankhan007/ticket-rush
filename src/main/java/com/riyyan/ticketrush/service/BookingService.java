package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.dto.request.CreateBookingRequest;
import com.riyyan.ticketrush.dto.response.BookingResponse;

public interface BookingService {

    BookingResponse bookSeats(CreateBookingRequest request);

}