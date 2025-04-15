package io.prozy.myfinance.mappers;


import io.prozy.myfinance.dto.BankDto;
import io.prozy.myfinance.entity.BankEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {
    BankDto toDto(BankEntity entity);
    BankEntity toEntity(BankDto dto);
}
