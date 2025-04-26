package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.TransactionStatusDto;
import io.prozy.myfinance.service.TransactionStatusesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/transactions/status")
@RequiredArgsConstructor
public class TransactionStatusesRestControllerV1 {
    private final TransactionStatusesService transactionStatusesService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<TransactionStatusDto> all = transactionStatusesService.getAll();

        if (Objects.nonNull(all)) {
            return ResponseEntity.ok(all);
        }
        return ResponseEntity.status(404).build();
    }
}
