package io.prozy.myfinance.dto;

import java.util.UUID;

public record TransactionTypeDto(
        UUID id,
        String name
) {}
