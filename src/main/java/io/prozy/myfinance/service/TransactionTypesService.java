package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.TransactionTypeDto;
import io.prozy.myfinance.entity.TransactionTypeEntity;
import io.prozy.myfinance.mappers.TransactionTypeMapper;
import io.prozy.myfinance.repository.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionTypesService {
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionTypeMapper transactionTypeMapper;

    public List<TransactionTypeDto> getAll() {
        List<TransactionTypeEntity> transactionTypeRepositoryAll = transactionTypeRepository.findAll();
        return transactionTypeRepositoryAll.stream().map(transactionTypeMapper::toDto).collect(Collectors.toList());
    }
}
