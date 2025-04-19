package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.TransactionDto;
import io.prozy.myfinance.entity.TransactionEntity;
import io.prozy.myfinance.repository.TransactionRepository;
import io.prozy.myfinance.mappers.BankMapper;
import io.prozy.myfinance.mappers.UserMapper;
import io.prozy.myfinance.mappers.TransactionStatusMapper;
import io.prozy.myfinance.mappers.TransactionTypeMapper;
import io.prozy.myfinance.mappers.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final UserMapper userMapper;  // Инжектируем UserMapper
  private final TransactionTypeMapper transactionTypeMapper;  // Инжектируем TransactionTypeMapper
  private final TransactionStatusMapper transactionStatusMapper;  // Инжектируем TransactionStatusMapper
  private final CategoryMapper categoryMapper;  // Инжектируем CategoryMapper
  private final BankMapper bankMapper;  // Инжектируем BankMapper

  @Autowired
  public TransactionService(TransactionRepository transactionRepository, 
                            UserMapper userMapper,
                            TransactionTypeMapper transactionTypeMapper,
                            TransactionStatusMapper transactionStatusMapper,
                            CategoryMapper categoryMapper,
                            BankMapper bankMapper) {
    this.transactionRepository = transactionRepository;
    this.userMapper = userMapper;
    this.transactionTypeMapper = transactionTypeMapper;
    this.transactionStatusMapper = transactionStatusMapper;
    this.categoryMapper = categoryMapper;
    this.bankMapper = bankMapper;
  }

  public List<TransactionDto> getTransactions(Double minAmount, Double maxAmount, LocalDateTime startDate,
                                              LocalDateTime endDate, String category) {
    List<TransactionEntity> transactions = transactionRepository.findByFilters(minAmount, maxAmount, startDate, endDate,
        category);

    return transactions.stream()
        .map(transaction -> new TransactionDto(
            transaction.getId(),
            userMapper.toDto(transaction.getUser()),  // Используем инжектированный userMapper
            transactionTypeMapper.toDto(transaction.getTransactionType()),  // Используем инжектированный transactionTypeMapper
            transactionStatusMapper.toDto(transaction.getTransactionStatus()),  // Используем инжектированный transactionStatusMapper
            categoryMapper.toDto(transaction.getCategoryEntity()),  // Используем инжектированный categoryMapper
            transaction.getTransactionDateTime(),
            transaction.getComment(),
            transaction.getAmount(),
            bankMapper.toDto(transaction.getSenderBankEntity()),  // Используем инжектированный bankMapper
            bankMapper.toDto(transaction.getRecipientBankEntity()),  // Используем инжектированный bankMapper
            transaction.getRecipientInn(),
            transaction.getRecipientBankAccount(),
            transaction.getRecipientPhone(),
            transaction.getCreatedAt(),
            transaction.getUpdatedAt()))
        .collect(Collectors.toList());
  }
}
