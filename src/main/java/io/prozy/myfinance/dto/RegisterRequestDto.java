package io.prozy.myfinance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

    @NotBlank(message = "Login is required")
    @Size(min = 4, max = 50, message = "Login must be between 4 and 50 characters")
    String login,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    String password,

    @Size(max = 100, message = "First name must be at most 100 characters")
    String firstName,

    @Size(max = 100, message = "Last name must be at most 100 characters")
    String lastName,

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must be at most 100 characters")
    String email

) {}
