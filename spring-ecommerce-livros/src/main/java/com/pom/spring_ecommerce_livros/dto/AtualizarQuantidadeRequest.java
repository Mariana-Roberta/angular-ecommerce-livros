package com.pom.spring_ecommerce_livros.dto;

public class AtualizarQuantidadeRequest {
  private Long carrinhoId;
  private Long itemId;
  private Integer quantidade;

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }
}
