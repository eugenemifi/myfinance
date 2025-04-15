package io.prozy.myfinance.dto;

import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        UUID id,
        UserDto user,
        TransactionTypeDto transactionType,
        TransactionStatusDto transactionStatus,
        CategoryDto category,
        LocalDateTime transactionDateTime,
        String comment,
        BigDecimal amount,
        BankDto senderBank,
        BankDto recipientBank,
        String recipientInn,
        String recipientBankAccount,
        String recipientPhone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

