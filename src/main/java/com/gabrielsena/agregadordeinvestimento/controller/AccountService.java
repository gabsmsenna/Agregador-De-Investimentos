package com.gabrielsena.agregadordeinvestimento.controller;

import com.gabrielsena.agregadordeinvestimento.controller.DTOS.AccountStockResponseDTO;
import com.gabrielsena.agregadordeinvestimento.controller.DTOS.AssociateAccountStockDTO;
import com.gabrielsena.agregadordeinvestimento.entity.AccountStock;
import com.gabrielsena.agregadordeinvestimento.entity.AccountStockId;
import com.gabrielsena.agregadordeinvestimento.repository.AccountRepository;
import com.gabrielsena.agregadordeinvestimento.repository.AccountStockRepository;
import com.gabrielsena.agregadordeinvestimento.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    private StockRepository stockRepository;

    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(String accountId, AssociateAccountStockDTO dto) {

        var account  = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock  = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // DTO -> ENTITY

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );

        accountStockRepository.save(entity);

    }

    public List<AccountStockResponseDTO> listStocks(String accountId) {

        var account  = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

       return account.getAccountStocks()
                .stream()
                .map(accountStock -> new AccountStockResponseDTO(
                        accountStock.getStock().getStockId(),
                        accountStock.getQuantity(),
                        0.0
                ))
                .toList();
    }
}
