package com.gabrielsena.agregadordeinvestimento.repository;

import com.gabrielsena.agregadordeinvestimento.entity.Account;
import com.gabrielsena.agregadordeinvestimento.entity.AccountStock;
import com.gabrielsena.agregadordeinvestimento.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
