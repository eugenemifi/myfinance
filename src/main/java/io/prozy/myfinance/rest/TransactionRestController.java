package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.TransactionDto;
import io.prozy.myfinance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionRestController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/search")
    public List<TransactionDto> searchTransactions(
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate,
            @RequestParam(required = false) String category) {
        return transactionService.getTransactions(minAmount, maxAmount, startDate, endDate, category);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<TransactionDto> all = transactionService.getAll();

        if (Objects.nonNull(all)) {
            return ResponseEntity.ok(all);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable("id") UUID uuid) {
        TransactionDto dto = transactionService.getById(uuid);
        if (Objects.nonNull(dto)) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/")
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDto dto = transactionService.addTransaction(transactionDto);
        if (Objects.nonNull(dto)) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/")
    public ResponseEntity<TransactionDto> updateTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDto dto = transactionService.addTransaction(transactionDto);
        if (Objects.nonNull(dto)) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(404).build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<TransactionDto> deleteTransaction(@PathVariable("id") UUID uuid) {
        TransactionDto dto = transactionService.deleteTransaction(uuid);
        if (Objects.nonNull(dto)) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(404).build();
    }
}
