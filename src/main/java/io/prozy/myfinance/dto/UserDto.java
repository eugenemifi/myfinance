package io.prozy.myfinance.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;
import java.time.LocalDateTime;

public record UserDto(
        UUID userId,
        String login,
        String firstName,
        String lastName,
        @Size(max = 254, message = "Email должен быть короче 254 символов")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Некорректный формат электронной почты"
        )
        String email,
        String userRole,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
