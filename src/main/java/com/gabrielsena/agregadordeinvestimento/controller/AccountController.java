package com.gabrielsena.agregadordeinvestimento.controller;

import com.gabrielsena.agregadordeinvestimento.controller.DTOS.AccountStockResponseDTO;
import com.gabrielsena.agregadordeinvestimento.controller.DTOS.AssociateAccountStockDTO;
import com.gabrielsena.agregadordeinvestimento.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock (@PathVariable("accountId") String accountId,
                                                @RequestBody AssociateAccountStockDTO dto) {

        accountService.associateStock(accountId, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> listAssociateStocks (@PathVariable("accountId") String accountId) {

        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }
}
