package com.gabrielsena.agregadordeinvestimento.service;


import com.gabrielsena.agregadordeinvestimento.controller.DTOS.CreateAccountDTO;
import com.gabrielsena.agregadordeinvestimento.controller.DTOS.CreateUserDTO;
import com.gabrielsena.agregadordeinvestimento.controller.DTOS.UpdateUserDTO;
import com.gabrielsena.agregadordeinvestimento.entity.Account;
import com.gabrielsena.agregadordeinvestimento.entity.BillingAddress;
import com.gabrielsena.agregadordeinvestimento.entity.User;
import com.gabrielsena.agregadordeinvestimento.repository.AccountRepository;
import com.gabrielsena.agregadordeinvestimento.repository.BillingAdressRepository;
import com.gabrielsena.agregadordeinvestimento.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAdressRepository billingAdressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAdressRepository billingAdressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAdressRepository = billingAdressRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {
       var entity =  new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),
                null
        );

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User>getUserById (String id) {
        var user = userRepository.findById(UUID.fromString(id));
        return user;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(String userId, UpdateUserDTO updateUserDTO) {
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDTO.username() != null) {
                user.setUsername(updateUserDTO.username());
            }

            if (updateUserDTO.password() != null) {
                user.setPassword(updateUserDTO.password());
            }

            userRepository.save(user);
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // DTO -> ENTITY

        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                createAccountDTO.description(),
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAdress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                createAccountDTO.street(),
                createAccountDTO.number()
        );

        billingAdressRepository.save(billingAdress);
    }
}
