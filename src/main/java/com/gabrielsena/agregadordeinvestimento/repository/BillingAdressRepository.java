package com.gabrielsena.agregadordeinvestimento.repository;

import com.gabrielsena.agregadordeinvestimento.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAdressRepository extends JpaRepository<BillingAddress, UUID> {
}
