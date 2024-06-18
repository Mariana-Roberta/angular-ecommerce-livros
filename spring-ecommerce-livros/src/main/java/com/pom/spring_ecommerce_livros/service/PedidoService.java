package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.dto.ItemCarrinhoRequest;
import com.pom.spring_ecommerce_livros.dto.PedidoRequest;
import com.pom.spring_ecommerce_livros.model.*;
import com.pom.spring_ecommerce_livros.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private ItemPedidoRepository itemPedidoRepository;

  @Autowired
  private CarrinhoRepository carrinhoRepository;

  @Autowired
  private ItemCarrinhoRepository itemCarrinhoRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public Pedido createPedidoFromCarrinho(Long carrinhoId) {
    Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
      .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

    Usuario usuario = carrinho.getUsuario();

    List<ItemCarrinho> itensCarrinho = itemCarrinhoRepository.findAllByCarrinhoId(carrinhoId);

    Double valorTotalPedido = 0.0;

    for (ItemCarrinho itemCarrinho : itensCarrinho) {
      Double valorItem = (itemCarrinho.getValor() * Double.valueOf(itemCarrinho.getQuantidade()));
      valorTotalPedido += valorItem;
    }

    Pedido pedido = new Pedido();
    pedido.setUsuario(usuario);
    pedido.setDataPedido(LocalDateTime.now());
    pedido.setStatus("PENDENTE");
    pedido.setValor(valorTotalPedido);
    pedido = pedidoRepository.save(pedido);
    //Factory Method
    Produto produto = FactoryProduto.getProduto("LIVRO");


    for (ItemCarrinho itemCarrinho : itensCarrinho) {
      ItemPedido itemPedido = new ItemPedido();
      itemPedido.setPedido(pedido);
      //Produto recebe tipo Livro
      produto = itemCarrinho.getLivro();
      itemPedido.setLivro(produto);

      itemPedido.setQuantidade(itemCarrinho.getQuantidade());
      itemPedido.setValor(itemCarrinho.getValor());

      itemPedidoRepository.save(itemPedido);
    }

    return pedido;
  }

  @Transactional
  public Pedido confirmarPedido(Long pedidoId) {
    Pedido pedido = pedidoRepository.findById(pedidoId)
      .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

    pedido.setStatus("FINALIZADO");
    pedidoRepository.save(pedido);

    /*Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
    if (carrinho != null) {
      // Delete os itens do carrinho
      itemCarrinhoRepository.deleteByCarrinhoId(carrinho.getId());

      // Delete o próprio carrinho
      carrinhoRepository.delete(carrinho);
    }*/

    return pedido;
  }
}

