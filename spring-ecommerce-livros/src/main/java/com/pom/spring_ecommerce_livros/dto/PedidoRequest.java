package com.pom.spring_ecommerce_livros.dto;

import com.pom.spring_ecommerce_livros.model.Usuario;

import java.util.List;

public class PedidoRequest {
  private Usuario usuario;
  private List<ItemCarrinhoRequest> itens;

  // Getters and Setters

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public List<ItemCarrinhoRequest> getItens() {
    return itens;
  }

  public void setItens(List<ItemCarrinhoRequest> itens) {
    this.itens = itens;
  }
}

