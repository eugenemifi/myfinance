package io.prozy.myfinance.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDto(
        UUID id,
        UserDto user,
        TransactionTypeDto transactionType,
        TransactionStatusDto transactionStatus,
        CategoryDto category,
        Long transactionDateTime,
        String comment,
        BigDecimal amount,
        BankDto senderBank,
        BankDto recipientBank,
        @Size(min = 11, max = 11, message = "ИНН должен содержать 11 цифр")
        @Pattern(regexp = "\\d+", message = "ИНН должен содержать только цифры")
        String recipientInn,
        String recipientBankAccount,
        @Size(min = 11, max = 12, message = "Номер телефона должен содержать 11 или 12 символов")
        @Pattern(
                regexp = "^(\\+[78]\\d{10}|[78]\\d{10})$",
                message = "Номер телефона должен быть в формате +7XXXXXXXXXX, 7XXXXXXXXXX или 8XXXXXXXXXX"
        )
        String recipientPhone,
        Long createdAt,
        Long updatedAt
) {
}

