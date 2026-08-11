package com.riyyan.ticketrush.auth.authorization;

import com.riyyan.ticketrush.entity.Theatre;
import com.riyyan.ticketrush.entity.User;
import com.riyyan.ticketrush.exception.TheatreNotFoundException;
import com.riyyan.ticketrush.repository.TheatreRepository;
import com.riyyan.ticketrush.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TheatreAuthorizationService {

    private final TheatreRepository theatreRepository;
    private final UserRepository userRepository;

    public Theatre getAuthorizedTheatre(Long theatreId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() ->
                        new TheatreNotFoundException(
                                "Theatre not found with id: " + theatreId
                        ));

        if ("ADMIN".equals(user.getRole().name())) {
            return theatre;
        }

        if (theatre.getOwner() == null ||
                !theatre.getOwner().getId().equals(user.getId())) {

            throw new AccessDeniedException(
                    "You do not have access to this theatre"
            );
        }

        return theatre;
    }
}