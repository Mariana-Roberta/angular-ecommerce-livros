package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.dto.AdicionarItemRequest;
import com.pom.spring_ecommerce_livros.dto.AtualizarQuantidadeRequest;
import com.pom.spring_ecommerce_livros.dto.DeleteItemRequest;
import com.pom.spring_ecommerce_livros.dto.UsuarioIdRequest;
import com.pom.spring_ecommerce_livros.model.Carrinho;
import com.pom.spring_ecommerce_livros.model.ItemCarrinho;
import com.pom.spring_ecommerce_livros.model.Livro;
import com.pom.spring_ecommerce_livros.model.Usuario;
import com.pom.spring_ecommerce_livros.service.CarrinhoService;
import com.pom.spring_ecommerce_livros.service.ItemCarrinhoService;
import com.pom.spring_ecommerce_livros.service.LivroService;
import com.pom.spring_ecommerce_livros.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class CarrinhoController {

  @Autowired
  private CarrinhoService carrinhoService;

  @Autowired
  private LivroService livroService;

  @Autowired
  private ItemCarrinhoService itemCarrinhoService;

  @PostMapping("/carrinho/addItem")
  public ResponseEntity<ItemCarrinho> adicionarOuAtualizarItemCarrinho(@RequestBody AdicionarItemRequest request) {
    Long carrinhoId = request.getCarrinhoId();
    Long livroId = request.getLivroId();
    Integer quantidade = request.getQuantidade();

    Carrinho carrinho = carrinhoService.findById(carrinhoId);
    Livro livro = livroService.findById(livroId);

    if (carrinho == null || livro == null) {
      return ResponseEntity.notFound().build();
    }

    Double valor = (quantidade * livro.getValor());
    ItemCarrinho itemExistente = itemCarrinhoService.findByCarrinhoIdAndLivroId(carrinhoId, livroId);

    if (itemExistente != null) {
      itemExistente.setQuantidade(quantidade);
      itemExistente.setValor(valor);
      itemCarrinhoService.save(itemExistente); // Atualiza o item existente
      return ResponseEntity.ok(itemExistente);
    } else {
      ItemCarrinho novoItem = itemCarrinhoService.addItemToCarrinho(carrinho.getId(), livro.getId(), quantidade, valor);
      return ResponseEntity.ok(novoItem);
    }
  }

  @GetMapping("/carrinho/itens")
  public ResponseEntity<List<ItemCarrinho>> getItensDoCarrinho(@RequestParam Long livroId) {
    List<ItemCarrinho> itens = itemCarrinhoService.findAllByLivroId(livroId);
    return ResponseEntity.ok(itens);
  }

  @GetMapping("/carrinho/listaItens")
  public ResponseEntity<List<ItemCarrinho>> getItensDoCarrinho() {
    List<ItemCarrinho> itens = itemCarrinhoService.findAll();
    return ResponseEntity.ok(itens);
  }

  @PutMapping("/carrinho/item/{itemId}")
  public ResponseEntity<ItemCarrinho> updateItemQuantity(
    @PathVariable Long itemId,
    @RequestBody AtualizarQuantidadeRequest request) {

    Integer novaQuantidade = request.getQuantidade();
    ItemCarrinho itemCarrinho = itemCarrinhoService.findById(itemId);

    if (itemCarrinho == null) {
      return ResponseEntity.notFound().build();
    }

    itemCarrinho.setQuantidade(novaQuantidade);
    itemCarrinhoService.save(itemCarrinho);

    return ResponseEntity.ok(itemCarrinho);
  }

  @DeleteMapping("/carrinho/deleteItem")
  public ResponseEntity<Void> deleteItemDoCarrinho(@RequestBody DeleteItemRequest deleteItemRequest) {
    boolean isRemoved = itemCarrinhoService.removeItem(deleteItemRequest.getItemId());
    if (!isRemoved) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping("/carrinho/criar")
  public ResponseEntity<Carrinho> criarCarrinho(@RequestBody UsuarioIdRequest request) {
    Long usuarioId = request.getUsuarioId();
    Carrinho novoCarrinho = carrinhoService.createCarrinho(usuarioId);
    return ResponseEntity.ok(novoCarrinho);
  }

  @GetMapping("/carrinho/user/{userId}")
  public ResponseEntity<Carrinho> getCartByUserId(@PathVariable Long userId) {
    Carrinho cart = carrinhoService.findCarrinhoByUsuarioId(userId);
    if (cart != null) {
      return ResponseEntity.ok(cart);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}

