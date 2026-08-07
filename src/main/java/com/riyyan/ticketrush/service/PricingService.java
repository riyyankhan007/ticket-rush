package com.riyyan.ticketrush.service;

import com.riyyan.ticketrush.entity.ShowSeat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PricingService {

    public BigDecimal calculate(List<ShowSeat> showSeats) {

        return showSeats.stream()
                .map(ShowSeat::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}