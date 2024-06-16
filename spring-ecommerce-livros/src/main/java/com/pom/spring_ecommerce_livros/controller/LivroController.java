package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.model.Livro;
import com.pom.spring_ecommerce_livros.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<List<Livro>> getAllLivro() {
    List<Livro> livros = livroService.findAll();
    return ResponseEntity.ok(livros);
  }

  @GetMapping("/search")
  public ResponseEntity<List<Livro>> searchBooks(@RequestParam String term) {
    List<Livro> livros = livroService.searchBooks(term);
    return ResponseEntity.ok(livros);
  }

  @GetMapping("/findByName")
  public ResponseEntity<Livro> findBookByName(@RequestParam String nome) {
    Livro livro = livroService.findBookByName(nome);
    if (livro != null) {
      return ResponseEntity.ok(livro);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

}
