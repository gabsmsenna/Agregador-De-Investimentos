package com.gabrielsena.agregadordeinvestimento.controller;

import com.gabrielsena.agregadordeinvestimento.DTOS.CreateUserDTO;
import com.gabrielsena.agregadordeinvestimento.DTOS.UpdateUserDTO;
import com.gabrielsena.agregadordeinvestimento.entity.User;
import com.gabrielsena.agregadordeinvestimento.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        var userId = userService.createUser(createUserDTO);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        var user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserById(@PathVariable("id") String id,
                                               @RequestBody UpdateUserDTO updateUserDTO) {

        userService.updateUserById(id, updateUserDTO);
        return ResponseEntity.noContent().build();
    }
}
