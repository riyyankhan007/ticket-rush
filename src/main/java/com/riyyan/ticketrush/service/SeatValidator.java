package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.entity.ShowSeat;
import com.riyyan.ticketrush.enums.SeatStatus;
import com.riyyan.ticketrush.exception.SeatAlreadyBookedException;
import com.riyyan.ticketrush.exception.ShowSeatNotFoundException;
import com.riyyan.ticketrush.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatValidator {

    private final ShowSeatRepository showSeatRepository;

    public List<ShowSeat> validate(
            Long showId,
            List<Long> seatIds) {

        List<Long> uniqueSeatIds = seatIds.stream()
                .distinct()
                .sorted()
                .toList();

        if (uniqueSeatIds.size() != seatIds.size()) {
            throw new IllegalArgumentException(
                    "Duplicate seats are not allowed"
            );
        }

        List<ShowSeat> showSeats =
                showSeatRepository.findAllByIdInAndShowId(
                        uniqueSeatIds,
                        showId
                );

        if (showSeats.size() != uniqueSeatIds.size()) {
            throw new ShowSeatNotFoundException(
                    "One or more seats do not belong to this show"
            );
        }

        for (ShowSeat showSeat : showSeats) {

            if (showSeat.getStatus() == SeatStatus.BOOKED) {

                String seatNumber =
                        showSeat.getSeat().getRowName()
                                + showSeat.getSeat().getSeatNumber();

                throw new SeatAlreadyBookedException(
                        "Seat already booked: " + seatNumber
                );
            }
        }

        return showSeats;
    }
}