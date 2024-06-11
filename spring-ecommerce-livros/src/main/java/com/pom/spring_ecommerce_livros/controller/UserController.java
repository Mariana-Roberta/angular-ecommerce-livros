package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.model.User;
import com.pom.spring_ecommerce_livros.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // GET /products : Listar todos os produtos
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
}
