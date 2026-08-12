package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieIdOrderByStartTimeAsc(Long movieId);

    boolean existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long screenId,
            java.time.LocalDateTime endTime,
            java.time.LocalDateTime startTime
    );
}