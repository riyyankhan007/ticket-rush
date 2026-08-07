package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.entity.Booking;
import com.riyyan.ticketrush.entity.Show;
import com.riyyan.ticketrush.entity.User;
import com.riyyan.ticketrush.enums.BookingStatus;
import com.riyyan.ticketrush.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingPersistenceService {

    private final BookingRepository bookingRepository;

    public Booking save(User user,
                        Show show,
                        BigDecimal totalAmount) {

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setTotalAmount(totalAmount);

        return bookingRepository.save(booking);
    }
}