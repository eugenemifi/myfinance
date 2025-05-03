package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.entity.BankEntity;
import io.prozy.myfinance.mappers.BankMapper;
import io.prozy.myfinance.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {

  private final BankRepository bankRepository;
  private final BankMapper bankMapper;

  public Page<BankDto> getAllBanks(Pageable pageable) {
    Page<BankEntity> banks = bankRepository.findAll(pageable);
    return banks.map(bankMapper::toDto);
  }
}
