package com.pom.spring_ecommerce_livros.dto;

public class PegarPeloCarrinhoEItem {
  private Long carrinhoId;
  private Long livroId;

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
}
