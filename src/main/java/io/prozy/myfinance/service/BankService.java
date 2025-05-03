package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.entity.BankEntity;
import io.prozy.myfinance.mappers.BankMapper;
import io.prozy.myfinance.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    public List<BankDto> getAll() {
        List<BankEntity> bankList = bankRepository.findAll();
        return bankList.stream().map(bankMapper::toDto).collect(Collectors.toList());
    }
}
