package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ShowRepository extends JpaRepository<Show, Long> {

    boolean existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long screenId,
            LocalDateTime endTime,
            LocalDateTime startTime
    );
}