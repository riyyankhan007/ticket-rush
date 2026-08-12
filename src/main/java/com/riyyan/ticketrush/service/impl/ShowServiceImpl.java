package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.auth.authorization.TheatreAuthorizationService;
import com.riyyan.ticketrush.dto.request.CreateShowRequest;
import com.riyyan.ticketrush.dto.response.ShowResponse;
import com.riyyan.ticketrush.dto.response.ShowSeatResponse;
import com.riyyan.ticketrush.entity.Movie;
import com.riyyan.ticketrush.entity.Screen;
import com.riyyan.ticketrush.entity.Seat;
import com.riyyan.ticketrush.entity.Show;
import com.riyyan.ticketrush.entity.ShowSeat;
import com.riyyan.ticketrush.enums.SeatStatus;
import com.riyyan.ticketrush.exception.ScreenNotFoundException;
import com.riyyan.ticketrush.exception.ShowOverlapException;
import com.riyyan.ticketrush.mapper.ShowMapper;
import com.riyyan.ticketrush.repository.MovieRepository;
import com.riyyan.ticketrush.repository.ScreenRepository;
import com.riyyan.ticketrush.repository.SeatRepository;
import com.riyyan.ticketrush.repository.ShowRepository;
import com.riyyan.ticketrush.repository.ShowSeatRepository;
import com.riyyan.ticketrush.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final TheatreAuthorizationService theatreAuthorizationService;

    @Override
    @Transactional
    public ShowResponse createShow(CreateShowRequest request) {

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                        new RuntimeException("Movie not found"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ScreenNotFoundException(
                                "Screen not found with id: "
                                        + request.getScreenId()
                        ));

        /*
         * This is the important authorization check.
         *
         * A theatre owner can only create a show on a screen
         * belonging to a theatre they own.
         *
         * ADMIN is allowed by TheatreAuthorizationService.
         */
        theatreAuthorizationService.getAuthorizedTheatre(
                screen.getTheatre().getId()
        );

        boolean overlappingShow =
                showRepository
                        .existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
                                screen.getId(),
                                request.getEndTime(),
                                request.getStartTime()
                        );

        if (overlappingShow) {
            throw new ShowOverlapException(
                    "A show already exists on this screen during the selected time"
            );
        }

        Show show = new Show();

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());

        Show savedShow = showRepository.save(show);

        List<Seat> seats =
                seatRepository.findByScreenId(screen.getId());

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

        return ShowMapper.toResponse(
                savedShow,
                request.getPrice()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByMovie(Long movieId) {

        return showRepository
                .findByMovieIdOrderByStartTimeAsc(movieId)
                .stream()
                .map(show ->
                        ShowMapper.toResponse(
                                show,
                                show.getShowSeats()
                                        .stream()
                                        .findFirst()
                                        .map(ShowSeat::getPrice)
                                        .orElse(BigDecimal.ZERO)
                        )
                )
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowSeatResponse> getSeats(Long showId) {

        if (!showRepository.existsById(showId)) {
            throw new RuntimeException(
                    "Show not found with id: " + showId
            );
        }

        return showSeatRepository.findByShowId(showId)
                .stream()
                .map(ShowMapper::toSeatResponse)
                .toList();
    }
}