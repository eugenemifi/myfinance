package io.prozy.myfinance.dto;

import java.util.UUID;

public record CategoryDto(
        UUID id,
        String categoryName,
        String categoryType
) {
}
