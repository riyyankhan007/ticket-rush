package com.riyyan.ticketrush.mapper;

import com.riyyan.ticketrush.dto.request.CreateMovieRequest;
import com.riyyan.ticketrush.dto.response.MovieResponse;
import com.riyyan.ticketrush.entity.Movie;

public class MovieMapper {

    public static Movie toEntity(CreateMovieRequest request) {

        Movie movie = new Movie();

        movie.setTitle(request.getTitle());
        movie.setLanguage(request.getLanguage());
        movie.setDuration(request.getDuration());
        movie.setGenre(request.getGenre());

        return movie;
    }

    public static MovieResponse toResponse(Movie movie) {

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .language(movie.getLanguage())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .createdAt(movie.getCreatedAt())
                .build();
    }
}