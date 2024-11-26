package com.gabrielsena.agregadordeinvestimento.controller;

import com.gabrielsena.agregadordeinvestimento.controller.DTOS.CreateStockDTO;
import com.gabrielsena.agregadordeinvestimento.entity.Stock;
import com.gabrielsena.agregadordeinvestimento.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO) {
        // DTO -> ENTITY

        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.stockDescription()
        );

        stockRepository.save(stock);
    }
}
