package com.gabrielsena.agregadordeinvestimento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    private Instant creationTimeStamp;

    @UpdateTimestamp
    private Instant updatedTimeStamp;

    @OneToMany(mappedBy = "user") //UM user possui UMA OU MAIS accounts
    private List<Account> accounts;

    public User(UUID userId, String username, String email, String password, Instant creationTimeStamp, Instant updatedTimeStamp) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationTimeStamp = creationTimeStamp;
        this.updatedTimeStamp = updatedTimeStamp;
    }
}
