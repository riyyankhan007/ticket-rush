package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.entity.ShowSeat;
import com.riyyan.ticketrush.enums.SeatStatus;
import com.riyyan.ticketrush.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatValidator {

    private final ShowSeatRepository showSeatRepository;

    public List<ShowSeat> validate(List<Long> seatIds) {

        List<ShowSeat> showSeats = showSeatRepository.findAllByIdIn(seatIds);

        if (showSeats.size() != seatIds.size()) {
            throw new RuntimeException("One or more seats not found");
        }

        for (ShowSeat showSeat : showSeats) {

            if (showSeat.getStatus() == SeatStatus.BOOKED) {
                throw new RuntimeException(
                        "Seat already booked: "
                                + showSeat.getSeat().getRowName()
                                + showSeat.getSeat().getSeatNumber()
                );
            }
        }

        return showSeats;
    }
}