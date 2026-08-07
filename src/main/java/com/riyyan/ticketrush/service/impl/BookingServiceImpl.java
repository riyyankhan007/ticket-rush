package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.dto.request.CreateBookingRequest;
import com.riyyan.ticketrush.dto.response.BookingResponse;
import com.riyyan.ticketrush.entity.Booking;
import com.riyyan.ticketrush.entity.Show;
import com.riyyan.ticketrush.entity.ShowSeat;
import com.riyyan.ticketrush.entity.User;
import com.riyyan.ticketrush.repository.ShowRepository;
import com.riyyan.ticketrush.repository.UserRepository;
import com.riyyan.ticketrush.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;

    private final SeatValidator seatValidator;
    private final ReservationService reservationService;
    private final PricingService pricingService;
    private final BookingPersistenceService bookingPersistenceService;

    @Override
    @Transactional
    public BookingResponse bookSeats(CreateBookingRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<ShowSeat> showSeats =
                seatValidator.validate(request.getShowSeatIds());

        BigDecimal totalAmount =
                pricingService.calculate(showSeats);

        reservationService.reserve(showSeats);

        Booking booking =
                bookingPersistenceService.save(user, show, totalAmount);

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .status(booking.getBookingStatus())
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .seats(
                        showSeats.stream()
                                .map(s -> s.getSeat().getRowName()
                                        + s.getSeat().getSeatNumber())
                                .toList()
                )
                .build();
    }
}