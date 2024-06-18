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
import java.util.List;

@Service
public class CarrinhoService {

  @Autowired
  private CarrinhoRepository carrinhoRepository;


  @Autowired
  private UsuarioRepository usuarioRepository;

  public List<Carrinho> findAll() {return this.carrinhoRepository.findAll();}

  public Carrinho findById(Long id) {return this.carrinhoRepository.findById(id).orElse(null);}

  public Carrinho createCarrinho(Usuario usuario) {
    Carrinho carrinho = new Carrinho();
    carrinho.setUsuario(usuario);
    return carrinhoRepository.save(carrinho);
  }

  public Carrinho createCarrinho(Long usuarioId) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));

    Carrinho carrinho = new Carrinho();
    carrinho.setUsuario(usuario);
    System.out.print(usuario);

    return carrinhoRepository.save(carrinho);
  }



  public Carrinho findCarrinhoByUsuarioId(Long usuarioId) {
    return this.carrinhoRepository.findByUsuarioId(usuarioId);
  }

}
