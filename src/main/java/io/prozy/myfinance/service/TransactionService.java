package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.TransactionDto;
import io.prozy.myfinance.entity.TransactionEntity;
import io.prozy.myfinance.mappers.*;
import io.prozy.myfinance.repository.TransactionRepository;
import io.prozy.myfinance.mappers.TransactionMapper;
import io.prozy.myfinance.repository.TransactionRepository;
import io.prozy.myfinance.utils.DateConverterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserMapper userMapper;
    private final TransactionTypeMapper transactionTypeMapper;
    private final TransactionStatusMapper transactionStatusMapper;
    private final CategoryMapper categoryMapper;
    private final BankMapper bankMapper;
    private final TransactionMapper transactionMapper;

    public List<TransactionDto> getTransactions(
            Double minAmount,
            Double maxAmount,
            Long startDate,
            Long endDate,
            String category) {
        List<TransactionEntity> transactions = transactionRepository.findByFilters(
                minAmount, maxAmount, fromEpochMilli(startDate), fromEpochMilli(endDate), category);
        return transactions.stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        userMapper.toDto(transaction.getUser()),
                        transactionTypeMapper.toDto(transaction.getTransactionType()),
                        transactionStatusMapper.toDto(transaction.getTransactionStatus()),
                        categoryMapper.toDto(transaction.getCategoryEntity()),
                        dateTimeToUnix(transaction.getTransactionDateTime()),
                        transaction.getComment(),
                        transaction.getAmount(),
                        bankMapper.toDto(transaction.getSenderBankEntity()),
                        bankMapper.toDto(transaction.getRecipientBankEntity()),
                        transaction.getRecipientInn(),
                        transaction.getRecipientBankAccount(),
                        transaction.getRecipientPhone(),
                        dateTimeToUnix(transaction.getCreatedAt()),
                        dateTimeToUnix(transaction.getUpdatedAt())))
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream().map(transactionMapper::toDto).collect(Collectors.toList());
    }

    public TransactionDto addTransaction(TransactionDto transactionDto) {
        TransactionEntity save = transactionRepository.save(transactionMapper.toEntity(transactionDto));
        return transactionMapper.toDto(save);
    }

    public TransactionDto deleteTransaction(UUID uuid) {
        Optional<TransactionEntity> deleted = transactionRepository.findById(uuid);
        if (deleted.isPresent()) {
            transactionRepository.deleteById(uuid);
            return transactionMapper.toDto(deleted.get());
        }
        return null;
    }

    public TransactionDto getById(UUID uuid) {
        Optional<TransactionEntity> byId = transactionRepository.findById(uuid);
        return byId.map(transactionMapper::toDto).orElse(null);
    }

    private Long dateTimeToUnix(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private LocalDateTime fromEpochMilli(Long millis) {
                return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
