package com.app.ecom.controller;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import com.app.ecom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getUsers(){
        return ResponseEntity.ok(service.fetchUsers());
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id){
        UserResponse user = service.fetchUserById(id);
        return Optional.ofNullable(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUsers(@RequestBody UserRequest user){
         service.createUser(user);
         return ResponseEntity.ok("User created successfu");
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int id, @RequestBody UserRequest user){
        return Optional.ofNullable(service.updateUser(id,user))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteUser(id));
    }

}
