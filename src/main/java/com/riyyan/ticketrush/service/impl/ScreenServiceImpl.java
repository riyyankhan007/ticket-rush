package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.auth.authorization.TheatreAuthorizationService;
import com.riyyan.ticketrush.dto.request.CreateScreenRequest;
import com.riyyan.ticketrush.dto.response.ScreenResponse;
import com.riyyan.ticketrush.entity.Screen;
import com.riyyan.ticketrush.entity.Seat;
import com.riyyan.ticketrush.entity.Theatre;
import com.riyyan.ticketrush.enums.SeatType;
import com.riyyan.ticketrush.mapper.ScreenMapper;
import com.riyyan.ticketrush.repository.ScreenRepository;
import com.riyyan.ticketrush.repository.SeatRepository;
import com.riyyan.ticketrush.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final TheatreAuthorizationService theatreAuthorizationService;

    @Override
    @Transactional
    public ScreenResponse createScreen(CreateScreenRequest request) {

        Theatre theatre =
                theatreAuthorizationService.getAuthorizedTheatre(
                        request.getTheatreId()
                );

        Screen screen = new Screen();

        screen.setName(request.getName());
        screen.setTotalSeats(
                request.getRows() * request.getSeatsPerRow()
        );
        screen.setTheatre(theatre);

        Screen savedScreen = screenRepository.save(screen);

        List<Seat> seats = new ArrayList<>();

        for (int row = 0; row < request.getRows(); row++) {

            char rowName = (char) ('A' + row);

            for (int seatNo = 1;
                 seatNo <= request.getSeatsPerRow();
                 seatNo++) {

                Seat seat = new Seat();

                seat.setScreen(savedScreen);
                seat.setRowName(String.valueOf(rowName));
                seat.setSeatNumber(seatNo);
                seat.setSeatType(SeatType.REGULAR);

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);

        return ScreenMapper.toResponse(savedScreen);
    }
}