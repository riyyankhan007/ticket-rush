package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}