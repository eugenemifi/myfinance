package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.service.BankGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
public class BankGeneratorController {

    private final BankGeneratorService bankGeneratorService;

    @PostMapping("/generate")
    public List<BankDto> generateBanks(@RequestParam(defaultValue = "5") int count) {
        return bankGeneratorService.generateBanks(count);
    }
}
