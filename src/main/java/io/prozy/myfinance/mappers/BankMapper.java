package io.prozy.myfinance.mappers;

import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.entity.BankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankMapper {

  @Mapping(source = "bankId", target = "bankId")
  @Mapping(source = "bankName", target = "bankName")
  @Mapping(source = "bicCode", target = "bicCode")
  BankDto toDto(BankEntity entity);

  @Mapping(source = "bankId", target = "bankId")
  @Mapping(source = "bankName", target = "bankName")
  @Mapping(source = "bicCode", target = "bicCode")

  BankEntity toEntity(BankDto dto);
}
