package com.gabrielsena.agregadordeinvestimento.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_billingadress")
@NoArgsConstructor
@Getter
@Setter
public class BillingAddress {

    @Id
    @Column(name = "account_id")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    public BillingAddress(UUID id, Account account, String street, Integer number) {
        this.id = id;
        this.account = account;
        this.street = street;
        this.number = number;
    }
}
