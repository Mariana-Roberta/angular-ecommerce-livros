package com.pom.spring_ecommerce_livros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_item_pedido")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_pedido", nullable = false)
  private Pedido pedido;

  @ManyToOne
  @JoinColumn(name = "id_livro", nullable = false)
  private Livro livro;

  @Column(name = "quantidade", nullable = false)
  private Integer quantidade;

  @Column(name = "valor", nullable = false)
  private Double valor;

  // Getters and setters

  public Long getId() {
    return id;
  }

  public Pedido getPedido() {
    return pedido;
  }

  public void setPedido(Pedido pedido) {
    this.pedido = pedido;
  }

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
