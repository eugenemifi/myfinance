package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.service.BankService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<BankDto> all = bankService.getAll();

        if (Objects.nonNull(all)) {
            return ResponseEntity.ok(all);
        }
        return ResponseEntity.status(404).build();
    }
}
