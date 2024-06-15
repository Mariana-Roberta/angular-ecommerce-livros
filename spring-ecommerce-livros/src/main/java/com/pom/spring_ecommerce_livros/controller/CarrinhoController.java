package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.model.Carrinho;
import com.pom.spring_ecommerce_livros.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario/carrinho")
public class CarrinhoController {

  @Autowired
  private CarrinhoService carrinhoService;

  @GetMapping("/criar")
  public ResponseEntity<?> criarCarrinho(@RequestParam Long usuarioId) {
    try {
      Carrinho carrinho = carrinhoService.criarCarrinho(usuarioId);
      return ResponseEntity.ok(carrinho);
    } catch (Exception e) {
      return ResponseEntity.status(500).body("{\"error\":\"" + e.getMessage() + "\"}");
    }
  }
}
