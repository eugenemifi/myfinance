package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.TransactionStatusDto;
import io.prozy.myfinance.entity.TransactionStatusEntity;
import io.prozy.myfinance.mappers.TransactionStatusMapper;
import io.prozy.myfinance.repository.TransactionStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionStatusesService {
    private final TransactionStatusRepository transactionStatusRepository;
    private final TransactionStatusMapper transactionStatusMapper;

    public List<TransactionStatusDto> getAll() {
        List<TransactionStatusEntity> transactionStatusRepositoryAll = transactionStatusRepository.findAll();
        return transactionStatusRepositoryAll.stream().map(transactionStatusMapper::toDto).collect(Collectors.toList());
    }
}
