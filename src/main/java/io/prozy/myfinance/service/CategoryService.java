package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.CategoryDto;
import io.prozy.myfinance.entity.CategoryEntity;
import io.prozy.myfinance.mappers.CategoryMapper;
import io.prozy.myfinance.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<CategoryDto> getAll() {
        List<CategoryEntity> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }
}
