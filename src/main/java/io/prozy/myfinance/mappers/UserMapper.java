package io.prozy.myfinance.mappers;

import io.prozy.myfinance.dto.UserDto;
import io.prozy.myfinance.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity entity);
    UserEntity toEntity(UserDto dto);
}
