package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.TransactionDto;
import io.prozy.myfinance.entity.TransactionEntity;
import io.prozy.myfinance.mappers.TransactionMapper;
import io.prozy.myfinance.repository.TransactionRepository;
import io.prozy.myfinance.utils.DateConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionDto> getTransactions(
            Double minAmount,
            Double maxAmount,
            Long startDate,
            Long endDate,
            String category) {
        List<TransactionEntity> transactions = transactionRepository.findByFilters(
                minAmount, maxAmount, DateConverterUtils.longToLdt(startDate), DateConverterUtils.longToLdt(endDate), category);
        return transactions.stream().map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream().map(transactionMapper::toDto).collect(Collectors.toList());
    }

    public TransactionDto addTransaction(TransactionDto transactionDto) {
        TransactionEntity save = transactionRepository.save(transactionMapper.toEntity(transactionDto));
        return transactionMapper.toDto(save);
    }
}
