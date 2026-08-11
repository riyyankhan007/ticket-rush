package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("""
            SELECT DISTINCT b
            FROM Booking b
            JOIN FETCH b.show s
            JOIN FETCH s.movie
            JOIN FETCH s.screen sc
            JOIN FETCH sc.theatre
            LEFT JOIN FETCH b.showSeats ss
            LEFT JOIN FETCH ss.seat
            WHERE b.id = :bookingId
            AND b.user.id = :userId
            """)
    Optional<Booking> findByIdAndUserIdWithDetails(
            @Param("bookingId") Long bookingId,
            @Param("userId") Long userId
    );
}