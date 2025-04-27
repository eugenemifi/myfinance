package io.prozy.myfinance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDto {
  private UUID bankId;
  private String bankName;
  private String bicCode;
}
