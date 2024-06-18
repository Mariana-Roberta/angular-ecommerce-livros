package com.pom.spring_ecommerce_livros.dto;

public class AtualizarComCarrinhoEItemRequest {
  private Long carrinhoId;
  private Long itemId;
  private Integer quantidade;

  public Long getCarrinhoId() {
    return carrinhoId;
  }

  public void setCarrinhoId(Long carrinhoId) {
    this.carrinhoId = carrinhoId;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }
}
