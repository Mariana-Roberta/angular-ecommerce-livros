package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.model.Livro;
import com.pom.spring_ecommerce_livros.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
@CrossOrigin("*")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping("/cadastro")
    public ResponseEntity<Livro> registerLivro(@RequestBody Livro usuario) {
        Livro savedUsuario = livroService.save(usuario);
        return ResponseEntity.ok(savedUsuario);
    }

    @GetMapping("/lista")
    public List<Livro> getAllLivro() {
        return livroService.findAll();
    }
}
