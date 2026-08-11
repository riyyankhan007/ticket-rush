package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.dto.request.CreateBookingRequest;
import com.riyyan.ticketrush.dto.response.BookingResponse;
import com.riyyan.ticketrush.dto.response.MyBookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse bookSeats(CreateBookingRequest request);

    List<MyBookingResponse> getMyBookings();

    MyBookingResponse getMyBooking(Long bookingId);
}