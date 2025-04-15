package io.prozy.myfinance.dto;

import java.util.UUID;

public record TransactionStatusDto(
        UUID id,
        String status
) {}
