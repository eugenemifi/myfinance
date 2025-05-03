package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.entity.BankEntity;
import io.prozy.myfinance.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankGeneratorService {

    private final BankRepository bankRepository;

    public List<BankDto> generateBanks(int count) {
        List<BankEntity> banks = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            BankEntity bank = new BankEntity();
            bank.setBankId(UUID.randomUUID());
            bank.setBankName("Bank_" + i);
            bank.setBicCode("BIK" + String.format("%07d", i));
            banks.add(bank);
        }

        List<BankEntity> savedBanks = bankRepository.saveAll(banks);

        return savedBanks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BankDto toDto(BankEntity bank) {
        return new BankDto(
            bank.getBankId(),
            bank.getBankName(),
            bank.getBicCode()
        );
    }
}
