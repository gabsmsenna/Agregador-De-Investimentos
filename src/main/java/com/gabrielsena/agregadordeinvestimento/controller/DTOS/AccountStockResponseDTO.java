package com.gabrielsena.agregadordeinvestimento.controller.DTOS;

public record AccountStockResponseDTO(String stockId,
                                      Integer quantity,
                                      Double total) {
}
