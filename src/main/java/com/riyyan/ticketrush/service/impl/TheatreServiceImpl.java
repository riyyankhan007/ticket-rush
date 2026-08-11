package com.riyyan.ticketrush.service.impl;

import com.riyyan.ticketrush.auth.authorization.TheatreAuthorizationService;
import com.riyyan.ticketrush.dto.request.CreateTheatreRequest;
import com.riyyan.ticketrush.dto.response.TheatreResponse;
import com.riyyan.ticketrush.entity.Theatre;
import com.riyyan.ticketrush.entity.User;
import com.riyyan.ticketrush.mapper.TheatreMapper;
import com.riyyan.ticketrush.repository.TheatreRepository;
import com.riyyan.ticketrush.repository.UserRepository;
import com.riyyan.ticketrush.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;
    private final UserRepository userRepository;
    private final TheatreAuthorizationService theatreAuthorizationService;

    @Override
    public TheatreResponse createTheatre(CreateTheatreRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Theatre theatre = TheatreMapper.toEntity(request);
        theatre.setOwner(owner);

        return TheatreMapper.toResponse(
                theatreRepository.save(theatre)
        );
    }

    @Override
    public List<TheatreResponse> getAllTheatres() {

        return theatreRepository.findAll()
                .stream()
                .map(TheatreMapper::toResponse)
                .toList();
    }

    @Override
    public List<TheatreResponse> getMyTheatres() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return theatreRepository.findByOwnerId(user.getId())
                .stream()
                .map(TheatreMapper::toResponse)
                .toList();
    }

    @Override
    public TheatreResponse updateTheatre(
            Long id,
            CreateTheatreRequest request) {

        Theatre theatre =
                theatreAuthorizationService.getAuthorizedTheatre(id);

        theatre.setName(request.getName());
        theatre.setCity(request.getCity());
        theatre.setAddress(request.getAddress());

        return TheatreMapper.toResponse(
                theatreRepository.save(theatre)
        );
    }

    @Override
    public void deleteTheatre(Long id) {

        Theatre theatre =
                theatreAuthorizationService.getAuthorizedTheatre(id);

        theatreRepository.delete(theatre);
    }
}