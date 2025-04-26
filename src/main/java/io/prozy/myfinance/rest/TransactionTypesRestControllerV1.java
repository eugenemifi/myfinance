package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.TransactionTypeDto;
import io.prozy.myfinance.service.TransactionTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/transactions/type")
@RequiredArgsConstructor
public class TransactionTypesRestControllerV1 {
    private final TransactionTypesService transactionTypesService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<TransactionTypeDto> all = transactionTypesService.getAll();

        if (Objects.nonNull(all)) {
            return ResponseEntity.ok(all);
        }
        return ResponseEntity.status(404).build();
    }
}
