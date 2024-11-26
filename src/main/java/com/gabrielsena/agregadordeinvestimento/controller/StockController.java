package com.gabrielsena.agregadordeinvestimento.controller;

import com.gabrielsena.agregadordeinvestimento.controller.DTOS.CreateStockDTO;
import com.gabrielsena.agregadordeinvestimento.controller.DTOS.CreateUserDTO;
import com.gabrielsena.agregadordeinvestimento.entity.Stock;
import com.gabrielsena.agregadordeinvestimento.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> creteStock (@RequestBody CreateStockDTO createStockDTO) {

        stockService.createStock(createStockDTO);

        return ResponseEntity.ok().build();
    }
}
