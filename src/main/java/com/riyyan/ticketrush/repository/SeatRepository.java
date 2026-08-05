package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}