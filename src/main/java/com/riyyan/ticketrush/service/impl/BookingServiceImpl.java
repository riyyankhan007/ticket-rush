package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.dto.request.CreateBookingRequest;
import com.riyyan.ticketrush.dto.response.BookingResponse;
import com.riyyan.ticketrush.dto.response.MyBookingResponse;
import com.riyyan.ticketrush.entity.Booking;
import com.riyyan.ticketrush.entity.Show;
import com.riyyan.ticketrush.entity.ShowSeat;
import com.riyyan.ticketrush.entity.User;
import com.riyyan.ticketrush.repository.BookingRepository;
import com.riyyan.ticketrush.repository.ShowRepository;
import com.riyyan.ticketrush.repository.UserRepository;
import com.riyyan.ticketrush.service.BookingPersistenceService;
import com.riyyan.ticketrush.service.BookingService;
import com.riyyan.ticketrush.service.PricingService;
import com.riyyan.ticketrush.service.ReservationService;
import com.riyyan.ticketrush.service.SeatValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;

    private final SeatValidator seatValidator;
    private final ReservationService reservationService;
    private final PricingService pricingService;
    private final BookingPersistenceService bookingPersistenceService;

    @Override
    @Transactional
    public BookingResponse bookSeats(CreateBookingRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Show not found with id: "
                                        + request.getShowId()
                        ));

        List<ShowSeat> showSeats =
                seatValidator.validate(
                        request.getShowId(),
                        request.getShowSeatIds()
                );

        BigDecimal totalAmount =
                pricingService.calculate(showSeats);

        reservationService.reserve(showSeats);

        Booking booking =
                bookingPersistenceService.save(
                        user,
                        show,
                        showSeats,
                        totalAmount
                );

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .status(booking.getBookingStatus())
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .seats(
                        showSeats.stream()
                                .map(s ->
                                        s.getSeat().getRowName()
                                                + s.getSeat().getSeatNumber()
                                )
                                .toList()
                )
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyBookingResponse> getMyBookings() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return bookingRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::toMyBookingResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MyBookingResponse getMyBooking(Long bookingId) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Booking booking =
                bookingRepository
                        .findByIdAndUserIdWithDetails(
                                bookingId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found"
                                ));

        return toMyBookingResponse(booking);
    }

    private MyBookingResponse toMyBookingResponse(
            Booking booking) {

        Show show = booking.getShow();

        return MyBookingResponse.builder()
                .bookingId(booking.getId())
                .movie(show.getMovie().getTitle())
                .theatre(show.getScreen().getTheatre().getName())
                .screen(show.getScreen().getName())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .seats(
                        booking.getShowSeats()
                                .stream()
                                .map(showSeat ->
                                        showSeat.getSeat().getRowName()
                                                + showSeat.getSeat().getSeatNumber()
                                )
                                .sorted()
                                .toList()
                )
                .totalAmount(booking.getTotalAmount())
                .status(booking.getBookingStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}