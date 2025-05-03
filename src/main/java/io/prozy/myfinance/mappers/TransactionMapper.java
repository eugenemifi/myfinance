package io.prozy.myfinance.mappers;

import io.prozy.myfinance.dto.TransactionDto;
import io.prozy.myfinance.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class,
        BankMapper.class,
        CategoryMapper.class,
        TransactionTypeMapper.class,
        TransactionStatusMapper.class,
        DateMapper.class
})
public interface TransactionMapper {

    @Mapping(source = "categoryEntity", target = "category") // Маппинг для Category
    @Mapping(source = "senderBankEntity", target = "senderBank") // Маппинг для senderBank
    @Mapping(source = "recipientBankEntity", target = "recipientBank") // Маппинг для recipientBank
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "localDateTimeToLong")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "localDateTimeToLong")
    @Mapping(source = "transactionDateTime", target = "transactionDateTime", qualifiedByName = "localDateTimeToLong")
    TransactionDto toDto(TransactionEntity entity);

    @Mapping(source = "category", target = "categoryEntity") // Маппинг для categoryEntity
    @Mapping(source = "senderBank", target = "senderBankEntity") // Маппинг для senderBankEntity
    @Mapping(source = "recipientBank", target = "recipientBankEntity") // Маппинг для recipientBankEntity
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "LongToLocalDateTime")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "LongToLocalDateTime")
    @Mapping(source = "transactionDateTime", target = "transactionDateTime", qualifiedByName = "LongToLocalDateTime")
    TransactionEntity toEntity(TransactionDto dto);
}
