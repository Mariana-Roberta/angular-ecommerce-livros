package com.pom.spring_ecommerce_livros.dto;

public class AdicionarItemRequest {
  private Long carrinhoId;
  private Long livroId;
  private Integer quantidade;

  // Getters and Setters
  public Long getCarrinhoId() {
    return carrinhoId;
  }

  public void setCarrinhoId(Long carrinhoId) {
    this.carrinhoId = carrinhoId;
  }

  public Long getLivroId() {
    return livroId;
  }

  public void setLivroId(Long livroId) {
    this.livroId = livroId;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }
}

