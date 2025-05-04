package io.prozy.myfinance.dto;

public record LoginResponseDto(
        String token,
        String uuid
) {}
