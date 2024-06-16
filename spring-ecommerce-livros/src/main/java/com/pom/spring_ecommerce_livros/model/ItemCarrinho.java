package com.pom.spring_ecommerce_livros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "itens_carrinho")
public class ItemCarrinho {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_item_carrinho")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_carrinho", nullable = false)
  private Carrinho carrinho;

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

  public Carrinho getCarrinho() {
    return carrinho;
  }

  public void setCarrinho(Carrinho carrinho) {
    this.carrinho = carrinho;
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
