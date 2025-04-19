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
    TransactionStatusMapper.class
})
public interface TransactionMapper {

  @Mapping(source = "categoryEntity", target = "category") // Маппинг для Category
  @Mapping(source = "senderBankEntity", target = "senderBank") // Маппинг для senderBank
  @Mapping(source = "recipientBankEntity", target = "recipientBank") // Маппинг для recipientBank
  TransactionDto toDto(TransactionEntity entity);

  @Mapping(source = "category", target = "categoryEntity") // Маппинг для categoryEntity
  @Mapping(source = "senderBank", target = "senderBankEntity") // Маппинг для senderBankEntity
  @Mapping(source = "recipientBank", target = "recipientBankEntity") // Маппинг для recipientBankEntity
  TransactionEntity toEntity(TransactionDto dto);
}
