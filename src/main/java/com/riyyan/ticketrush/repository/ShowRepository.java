package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}