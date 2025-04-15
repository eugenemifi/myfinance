package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.CategoryDto;
import io.prozy.myfinance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestControllerV1 {

    private final CategoryService categoryService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID uuid) {
        CategoryDto item = categoryService.getById(uuid);
        if(Objects.nonNull(item)) {
            return ResponseEntity.ok(item);
        }

        return ResponseEntity.status(404).build();
    }
}
