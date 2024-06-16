package com.pom.spring_ecommerce_livros.dto;

public class FinalizarPedidoRequest {
  private Long carrinhoId;

  public Long getCarrinhoId() {
    return carrinhoId;
  }

  public void setCarrinhoId(Long carrinhoId) {
    this.carrinhoId = carrinhoId;
  }
}
