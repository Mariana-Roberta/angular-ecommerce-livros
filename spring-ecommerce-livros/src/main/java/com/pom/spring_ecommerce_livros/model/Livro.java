package com.pom.spring_ecommerce_livros.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("LIVRO")
//@Table(name = "livros")
public class Livro extends Produto{
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "sinopse", nullable = false)
    private String sinopse;

    //@Column(name = "valor", nullable = false)
    //private double valor;

    @Column(name = "qtd", nullable = false)
    private Integer quantidade;


    public Livro(String titulo, String autor, String sinopse, double valor, Integer quantidade) {
        super(valor);
        this.titulo = titulo;
        this.autor = autor;
        this.sinopse = sinopse;
        this.quantidade = quantidade;
    }

    public Livro(){
    }

    //public Long getId() {
    //    return id;
    //}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
