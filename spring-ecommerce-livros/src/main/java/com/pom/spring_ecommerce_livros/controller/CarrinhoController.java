package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.dto.*;
import com.pom.spring_ecommerce_livros.model.*;
import com.pom.spring_ecommerce_livros.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario/carrinho")
@CrossOrigin("*")
public class CarrinhoController {

  @Autowired
  private CarrinhoService carrinhoService;

  @Autowired
  private LivroService livroService;

  @Autowired
  private ItemCarrinhoService itemCarrinhoService;
  @Autowired
  private ProdutoService produtoService;

  @PostMapping("/addItem")
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

  @GetMapping("/itens")
  public ResponseEntity<List<ItemCarrinho>> getItensDoCarrinho(@RequestParam Long livroId) {
    List<ItemCarrinho> itens = itemCarrinhoService.findAllByLivroId(livroId);
    return ResponseEntity.ok(itens);
  }

  @GetMapping("/listaItens")
  public ResponseEntity<List<ItemCarrinho>> getItensDoCarrinho() {
    List<ItemCarrinho> itens = itemCarrinhoService.findAll();
    return ResponseEntity.ok(itens);
  }

  @PutMapping("/book/cart")
  public ResponseEntity<ItemCarrinho> updateItemQuantityByLivroId(@RequestBody AtualizarComCarrinhoELivroRequest request) {
    Long carrinhoId = request.getCarrinhoId();
    Long livroId = request.getLivroId();
    Integer novaQuantidade = request.getQuantidade();
    ItemCarrinho itemCarrinho = itemCarrinhoService.findByCarrinhoIdAndLivroId(carrinhoId, livroId);

    if (itemCarrinho == null) {
      ItemCarrinho itemNovo = new ItemCarrinho();
      Carrinho carrinho = carrinhoService.findById(carrinhoId);
      Livro livro = livroService.findById(livroId);
      itemNovo.setCarrinho(carrinho);
      itemNovo.setLivro(livro);
      itemNovo.setQuantidade(novaQuantidade);
      itemNovo.setValor((novaQuantidade * livro.getValor()));
      itemCarrinhoService.save(itemNovo);
      return ResponseEntity.ok(itemNovo);
    }

    itemCarrinho.setQuantidade(novaQuantidade);
    itemCarrinhoService.save(itemCarrinho);

    return ResponseEntity.ok(itemCarrinho);
  }

  @PutMapping("/item/cart")
  public ResponseEntity<ItemCarrinho> updateItemQuantityByItemId(@RequestBody AtualizarComCarrinhoEItemRequest request) {
    Long carrinhoId = request.getCarrinhoId();
    Long itemId = request.getItemId();
    Integer novaQuantidade = request.getQuantidade();
    ItemCarrinho itemCarrinho = itemCarrinhoService.findById(


      itemId);

    if (itemCarrinho == null) {
      return ResponseEntity.notFound().build();
    }

    itemCarrinho.setQuantidade(novaQuantidade);
    itemCarrinhoService.save(itemCarrinho);

    return ResponseEntity.ok(itemCarrinho);
  }


  @DeleteMapping("/deleteItem")
  public ResponseEntity<Void> deleteItemDoCarrinho(@RequestBody DeleteItemRequest deleteItemRequest) {
    boolean isRemoved = itemCarrinhoService.removeItem(deleteItemRequest.getItemId());
    if (!isRemoved) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping("/criar")
  public ResponseEntity<Carrinho> criarCarrinho(@RequestBody UsuarioIdRequest request) {
    Long usuarioId = request.getUsuarioId();
    Carrinho novoCarrinho = carrinhoService.createCarrinho(usuarioId);
    return ResponseEntity.ok(novoCarrinho);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<Carrinho> getCartByUserId(@PathVariable Long userId) {
    System.out.println("CHEGOU AQUI");
    Carrinho cart = carrinhoService.findCarrinhoByUsuarioId(userId);
    System.out.println(cart);
    if (cart != null) {
      return ResponseEntity.ok(cart);
    } else {
      Carrinho carrinhoCriado = carrinhoService.createCarrinho(userId);
      return ResponseEntity.ok(carrinhoCriado);
    }
  }

  @GetMapping("/cart/{carrinhoId}")
  public ResponseEntity<Carrinho> getCart(@PathVariable Long carrinhoId) {
    System.out.println("CHEGOU AQUI");
    Carrinho cart = carrinhoService.findById(carrinhoId);
    System.out.println(cart);
    if (cart != null) {
      return ResponseEntity.ok(cart);
    } else {
      return ResponseEntity.ok().build();
    }
  }
}

