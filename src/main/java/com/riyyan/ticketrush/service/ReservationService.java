package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.entity.ShowSeat;
import com.riyyan.ticketrush.enums.SeatStatus;
import com.riyyan.ticketrush.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ShowSeatRepository showSeatRepository;

    public void reserve(List<ShowSeat> showSeats) {

        showSeats.forEach(seat ->
                seat.setStatus(SeatStatus.BOOKED));

        showSeatRepository.saveAll(showSeats);
    }
}