package io.prozy.myfinance.dto;

import java.util.UUID;

public record BankDto(
        UUID bankId,
        String bankName,
        String bicCode
) {}
