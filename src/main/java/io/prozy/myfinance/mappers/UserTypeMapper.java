package io.prozy.myfinance.mappers;

import io.prozy.myfinance.dto.UserTypeDto;
import io.prozy.myfinance.entity.UserTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    UserTypeDto toDto(UserTypeEntity entity);
    UserTypeEntity toEntity(UserTypeDto dto);
}
