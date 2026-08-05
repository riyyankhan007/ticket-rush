package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.dto.request.CreateMovieRequest;
import com.riyyan.ticketrush.dto.response.MovieResponse;

import java.util.List;

public interface MovieService {

    MovieResponse createMovie(CreateMovieRequest request);

    List<MovieResponse> getAllMovies();

    MovieResponse getMovieById(Long id);

    void deleteMovie(Long id);
}