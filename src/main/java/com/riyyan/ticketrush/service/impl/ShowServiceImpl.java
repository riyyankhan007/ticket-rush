package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.dto.request.CreateShowRequest;
import com.riyyan.ticketrush.dto.response.ShowResponse;
import com.riyyan.ticketrush.dto.response.ShowSeatResponse;
import com.riyyan.ticketrush.entity.*;
import com.riyyan.ticketrush.enums.SeatStatus;
import com.riyyan.ticketrush.mapper.ShowMapper;
import com.riyyan.ticketrush.repository.*;
import com.riyyan.ticketrush.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;

    @Override
    public ShowResponse createShow(CreateShowRequest request) {

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());

        Show savedShow = showRepository.save(show);

        List<Seat> seats = seatRepository.findByScreenId(screen.getId());

        List<ShowSeat> showSeats = new ArrayList<>();

        for (Seat seat : seats) {

            ShowSeat showSeat = new ShowSeat();

            showSeat.setShow(savedShow);
            showSeat.setSeat(seat);
            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeat.setPrice(request.getPrice());

            showSeats.add(showSeat);
        }

        showSeatRepository.saveAll(showSeats);

        return ShowMapper.toResponse(savedShow, request.getPrice());
    }

    @Override
    public List<ShowSeatResponse> getSeats(Long showId) {

        return showSeatRepository.findByShowId(showId)
                .stream()
                .map(ShowMapper::toSeatResponse)
                .toList();
    }
}