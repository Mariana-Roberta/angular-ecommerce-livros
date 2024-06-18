package com.pom.spring_ecommerce_livros.controller;


import com.pom.spring_ecommerce_livros.model.Livro;
import com.pom.spring_ecommerce_livros.model.Produto;
import com.pom.spring_ecommerce_livros.service.LivroService;
import com.pom.spring_ecommerce_livros.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class ProdutoController {

  @Autowired
  private ProdutoService produtoService;

  @GetMapping("/lista")
  public ResponseEntity<List<Produto>> getAllLivro() {
    List<Produto> produtos = produtoService.findAll();
    System.out.println("Livros do banco:" + produtos);
    return ResponseEntity.ok(produtos);
  }
}
