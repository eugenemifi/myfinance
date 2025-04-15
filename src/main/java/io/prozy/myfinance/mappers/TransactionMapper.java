package io.prozy.myfinance.mappers;

import io.prozy.myfinance.dto.TransactionDto;
import io.prozy.myfinance.entity.TransactionEntity;
import org.mapstruct.Mapper;


@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class,
                BankMapper.class,
                CategoryMapper.class,
                TransactionTypeMapper.class,
                TransactionStatusMapper.class
        }
)
public interface TransactionMapper {
    TransactionDto toDto(TransactionEntity entity);

    TransactionEntity toEntity(TransactionDto dto);
}
