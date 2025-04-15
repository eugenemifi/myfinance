package io.prozy.myfinance.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public record UserDto(
        UUID userId,
        String login,
        String firstName,
        String lastName,
        String email,
        String userRole,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
