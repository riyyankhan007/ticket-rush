package com.riyyan.ticketrush.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateScreenRequest {

    @NotNull
    private Long theatreId;

    @NotBlank
    private String name;

    @Min(1)
    private Integer rows;

    @Min(1)
    private Integer seatsPerRow;
}