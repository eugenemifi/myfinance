package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.TransactionDto;
import io.prozy.myfinance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
      @RequestParam(required = false) LocalDateTime startDate,
      @RequestParam(required = false) LocalDateTime endDate,
      @RequestParam(required = false) String category) {
    return transactionService.getTransactions(minAmount, maxAmount, startDate, endDate, category);
  }
}
