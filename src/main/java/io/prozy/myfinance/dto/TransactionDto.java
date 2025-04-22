package io.prozy.myfinance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
        Long createdAt,
        Long updatedAt
) {
}

