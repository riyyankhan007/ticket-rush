package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.entity.Booking;
import com.riyyan.ticketrush.entity.Show;
import com.riyyan.ticketrush.entity.ShowSeat;
import com.riyyan.ticketrush.entity.User;
import com.riyyan.ticketrush.enums.BookingStatus;
import com.riyyan.ticketrush.repository.BookingRepository;
import com.riyyan.ticketrush.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingPersistenceService {

    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;

    public Booking save(
            User user,
            Show show,
            List<ShowSeat> showSeats,
            BigDecimal totalAmount) {

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setTotalAmount(totalAmount);

        Booking savedBooking = bookingRepository.save(booking);

        showSeats.forEach(showSeat ->
                showSeat.setBooking(savedBooking)
        );

        showSeatRepository.saveAll(showSeats);

        return savedBooking;
    }
}