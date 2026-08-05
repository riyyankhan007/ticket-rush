package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
}