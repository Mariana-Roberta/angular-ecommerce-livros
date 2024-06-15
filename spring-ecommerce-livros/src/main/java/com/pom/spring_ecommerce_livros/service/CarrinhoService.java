package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.model.Carrinho;
import com.pom.spring_ecommerce_livros.model.ItemCarrinho;
import com.pom.spring_ecommerce_livros.model.Livro;
import com.pom.spring_ecommerce_livros.model.Usuario;
import com.pom.spring_ecommerce_livros.repository.CarrinhoRepository;
import com.pom.spring_ecommerce_livros.repository.ItemCarrinhoRepository;
import com.pom.spring_ecommerce_livros.repository.LivroRepository;
import com.pom.spring_ecommerce_livros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CarrinhoService {

  @Autowired
  private CarrinhoRepository carrinhoRepository;

  @Autowired
  private ItemCarrinhoRepository itemCarrinhoRepository;

  @Autowired
  private LivroRepository livroRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public Carrinho criarCarrinho(Long usuarioId) {
    Carrinho carrinho = new Carrinho();
    Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
    carrinho.setUsuario(usuario);
    carrinho.setDataCriacao(new Date());
    return carrinhoRepository.save(carrinho);
  }

  public ItemCarrinho adicionarItemAoCarrinho(Long carrinhoId, Long livroId, Integer quantidade) {
    Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
    Livro livro = livroRepository.findById(livroId).orElseThrow();

    ItemCarrinho itemCarrinho = new ItemCarrinho();
    itemCarrinho.setCarrinho(carrinho);
    itemCarrinho.setLivro(livro);
    itemCarrinho.setQuantidade(quantidade);

    return itemCarrinhoRepository.save(itemCarrinho);
  }
}
