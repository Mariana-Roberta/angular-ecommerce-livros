package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.repository.UserRepository;
import com.pom.spring_ecommerce_livros.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
