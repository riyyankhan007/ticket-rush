package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.dto.request.CreateBookingRequest;
import com.riyyan.ticketrush.dto.response.BookingResponse;
import com.riyyan.ticketrush.entity.Booking;
import com.riyyan.ticketrush.entity.Show;
import com.riyyan.ticketrush.entity.ShowSeat;
import com.riyyan.ticketrush.entity.User;
import com.riyyan.ticketrush.enums.BookingStatus;
import com.riyyan.ticketrush.enums.SeatStatus;
import com.riyyan.ticketrush.repository.BookingRepository;
import com.riyyan.ticketrush.repository.ShowRepository;
import com.riyyan.ticketrush.repository.ShowSeatRepository;
import com.riyyan.ticketrush.repository.UserRepository;
import com.riyyan.ticketrush.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;

    @Override
    @Transactional
    public BookingResponse bookSeats(CreateBookingRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<ShowSeat> showSeats =
                showSeatRepository.findAllByIdIn(request.getShowSeatIds());

        for (ShowSeat showSeat : showSeats) {

            if (showSeat.getStatus() == SeatStatus.BOOKED) {
                throw new RuntimeException(
                        "Seat already booked : "
                                + showSeat.getSeat().getRowName()
                                + showSeat.getSeat().getSeatNumber()
                );
            }
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ShowSeat showSeat : showSeats) {

            showSeat.setStatus(SeatStatus.BOOKED);

            totalAmount = totalAmount.add(showSeat.getPrice());
        }

        showSeatRepository.saveAll(showSeats);

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setTotalAmount(totalAmount);

        Booking savedBooking = bookingRepository.save(booking);

        return BookingResponse.builder()
                .bookingId(savedBooking.getId())
                .status(savedBooking.getBookingStatus())
                .totalAmount(savedBooking.getTotalAmount())
                .createdAt(savedBooking.getCreatedAt())
                .seats(
                        showSeats.stream()
                                .map(s ->
                                        s.getSeat().getRowName()
                                                + s.getSeat().getSeatNumber())
                                .toList()
                )
                .build();
    }
}