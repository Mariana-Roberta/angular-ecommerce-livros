package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.model.Carrinho;
import com.pom.spring_ecommerce_livros.model.Livro;
import com.pom.spring_ecommerce_livros.model.Produto;
import com.pom.spring_ecommerce_livros.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

  @Autowired
  private ProdutoRepository produtoRepository;

  public List<Produto> findAll() {return this.produtoRepository.findAll();}

  public Produto findById(Long id) {return this.produtoRepository.findById(id).orElse(null);}

  public Produto save(Produto produto) {
    return produtoRepository.save(produto);
  }

  public void delete(Long id){
    this.produtoRepository.deleteById(id);
  }
}
