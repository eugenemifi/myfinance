package io.prozy.myfinance.mappers;

import io.prozy.myfinance.dto.CategoryDto;
import io.prozy.myfinance.entity.CategoryEntity;
import org.mapstruct.Mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(CategoryEntity entity);
    CategoryEntity toEntity(CategoryDto dto);
}
