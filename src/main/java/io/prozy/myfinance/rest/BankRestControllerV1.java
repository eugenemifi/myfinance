package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankRestControllerV1 {
    private final BankService bankService;

    @GetMapping
    public ResponseEntity<Page<BankDto>> getAll(Pageable pageable) {
        Page<BankDto> banks = bankService.getAllBanks(pageable);
        return ResponseEntity.ok(banks);
    }

}
