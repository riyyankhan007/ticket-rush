package com.riyyan.ticketrush.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMovieRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Language is required")
    private String language;

    @Min(value = 1, message = "Duration must be positive")
    private Integer duration;

    @NotBlank(message = "Genre is required")
    private String genre;
}