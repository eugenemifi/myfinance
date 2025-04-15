package io.prozy.myfinance.dto;

import java.util.UUID;

public record UserTypeDto(
        UUID id,
        String name
) {}
