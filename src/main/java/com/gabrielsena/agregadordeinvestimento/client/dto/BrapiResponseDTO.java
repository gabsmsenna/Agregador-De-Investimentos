package com.gabrielsena.agregadordeinvestimento.client.dto;

import java.util.List;

public record BrapiResponseDTO(
        List<StockDto> results
) {
}
