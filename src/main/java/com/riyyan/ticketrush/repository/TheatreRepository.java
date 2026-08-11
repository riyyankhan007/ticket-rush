package com.riyyan.ticketrush.repository;

import com.riyyan.ticketrush.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    List<Theatre> findByOwnerId(Long ownerId);

    Optional<Theatre> findByIdAndOwnerId(Long theatreId, Long ownerId);
}