package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}