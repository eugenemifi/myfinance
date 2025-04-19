package io.prozy.myfinance.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequestDto{
    @NotBlank(message = "Login is required")
    private String login;

    @NotBlank(message = "Password is required")
    private String password;
}
