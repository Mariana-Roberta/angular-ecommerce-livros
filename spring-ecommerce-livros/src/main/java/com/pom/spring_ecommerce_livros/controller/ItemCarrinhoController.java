package com.pom.spring_ecommerce_livros.controller;
// ItemCarrinhoController.java

import com.pom.spring_ecommerce_livros.model.ItemCarrinho;
import com.pom.spring_ecommerce_livros.service.ItemCarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario/carrinho")
@CrossOrigin("*")
public class ItemCarrinhoController {

  private final ItemCarrinhoService itemCarrinhoService;

  @Autowired
  public ItemCarrinhoController(ItemCarrinhoService itemCarrinhoService) {
    this.itemCarrinhoService = itemCarrinhoService;
  }

  @GetMapping("/users/{userId}/carrinho/items")
  public ResponseEntity<List<ItemCarrinho>> getItensCarrinhoByUserId(@PathVariable Long userId) {
    List<ItemCarrinho> itensCarrinho = itemCarrinhoService.findByUserId(userId);
    return ResponseEntity.ok(itensCarrinho);
  }

  // Outros métodos conforme necessário
}

