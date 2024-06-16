package com.pom.spring_ecommerce_livros.dto;

import com.pom.spring_ecommerce_livros.model.Livro;

public class ItemCarrinhoRequest {
  private Livro livro;
  private Integer quantidade;
  private Double valor;

  // Getters and Setters
  public Livro getLivro() {
    return livro;
  }

  public void setLivro(Livro livro) {
    this.livro = livro;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }
}

