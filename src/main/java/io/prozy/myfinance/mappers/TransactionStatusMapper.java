package io.prozy.myfinance.mappers;

import io.prozy.myfinance.dto.TransactionStatusDto;
import io.prozy.myfinance.entity.TransactionStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionStatusMapper {
    TransactionStatusDto toDto(TransactionStatusEntity entity);
    TransactionStatusEntity toEntity(TransactionStatusDto dto);
}
