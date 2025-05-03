
package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping
    public Page<BankDto> getAllBanks(Pageable pageable) {
        return bankService.getAllBanks(pageable);
    }
}
