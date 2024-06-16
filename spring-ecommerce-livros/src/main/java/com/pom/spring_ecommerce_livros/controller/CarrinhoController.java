package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.dto.AdicionarItemRequest;
import com.pom.spring_ecommerce_livros.dto.AtualizarQuantidadeRequest;
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
@RequestMapping("/usuario/carrinho")
@CrossOrigin("*")
public class CarrinhoController {

  @Autowired
  private CarrinhoService carrinhoService;

  @Autowired
  private LivroService livroService;

  @Autowired
  private ItemCarrinhoService itemCarrinhoService;

  // Adicionar item ao carrinho ou atualizar a quantidade se já existir
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

    // Verifica se o livro já está no carrinho
    ItemCarrinho itemExistente = itemCarrinhoService.findByCarrinhoIdAndLivroId(carrinhoId, livroId);

    if (itemExistente != null) {
      // Se o item já existe, atualiza a quantidade
      itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
      itemCarrinhoService.save(itemExistente); // Atualiza o item existente
      return ResponseEntity.ok(itemExistente);
    } else {
      // Se o item não existe, cria um novo
      ItemCarrinho novoItem = itemCarrinhoService.addItemToCarrinho(carrinho.getId(), livro.getId(), quantidade);
      return ResponseEntity.ok(novoItem);
    }
  }

  // Métodos adicionais conforme necessário para buscar itens, criar carrinho, etc.
  // Exemplo:

  // Endpoint para buscar todos os itens do carrinho
  @GetMapping("/itens")
  public ResponseEntity<List<ItemCarrinho>> getItensDoCarrinho(@RequestParam Long livroId) {
    List<ItemCarrinho> itens = itemCarrinhoService.findAllByLivroId(livroId);
    return ResponseEntity.ok(itens);
  }

  @PutMapping("/item/{itemId}")
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

  @PostMapping("/criar")
  public ResponseEntity<Carrinho> criarCarrinho(@RequestBody UsuarioIdRequest request) {
    Long usuarioId = request.getUsuarioId();
    Carrinho novoCarrinho = carrinhoService.createCarrinho(usuarioId);
    return ResponseEntity.ok(novoCarrinho);
  }
}

