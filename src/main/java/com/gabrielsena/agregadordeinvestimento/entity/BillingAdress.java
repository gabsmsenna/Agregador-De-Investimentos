package com.gabrielsena.agregadordeinvestimento.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_billingadress")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingAdress {

    @Id
    @Column(name = "account_id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;
}
