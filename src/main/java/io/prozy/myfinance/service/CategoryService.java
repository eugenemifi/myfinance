package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.CategoryDto;
import io.prozy.myfinance.entity.CategoryEntity;
import io.prozy.myfinance.mappers.CategoryMapper;
import io.prozy.myfinance.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto getById(UUID uuid) {
        CategoryEntity categoryEntity = categoryRepository.findById(uuid)
                .orElseThrow();
        return categoryMapper.toDto(categoryEntity);
    }
}
