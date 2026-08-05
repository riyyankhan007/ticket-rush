package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.dto.request.CreateMovieRequest;
import com.riyyan.ticketrush.dto.response.MovieResponse;
import com.riyyan.ticketrush.entity.Movie;
import com.riyyan.ticketrush.mapper.MovieMapper;
import com.riyyan.ticketrush.repository.MovieRepository;
import com.riyyan.ticketrush.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.riyyan.ticketrush.exception.MovieNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieResponse createMovie(CreateMovieRequest request) {

        Movie movie = MovieMapper.toEntity(request);

        Movie savedMovie = movieRepository.save(movie);

        return MovieMapper.toResponse(savedMovie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    @Override
    public MovieResponse getMovieById(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        return MovieMapper.toResponse(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}