package io.prozy.myfinance.mappers;

import io.prozy.myfinance.dto.TransactionTypeDto;
import io.prozy.myfinance.entity.TransactionTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper {
    TransactionTypeDto toDto(TransactionTypeEntity entity);
    TransactionTypeEntity toEntity(TransactionTypeDto dto);
}
