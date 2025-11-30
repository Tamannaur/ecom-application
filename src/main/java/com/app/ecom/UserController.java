package com.app.ecom;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(service.fetchUsers());
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        User user = service.fetchUserById(id);
        return Optional.ofNullable(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/user")
    public ResponseEntity<String> createUsers(@RequestBody User user){
         service.createUser(user);
         return ResponseEntity.ok("User created successfu");
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<List<User>> updateUser(@PathVariable int id, @RequestBody User user){
        return Optional.ofNullable(service.updateUser(id,user))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
