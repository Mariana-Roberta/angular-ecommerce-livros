package com.pom.spring_ecommerce_livros.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;

@Entity
@Inheritance()
@Table(name = "livros")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livro")
    long id;
    
    @Column(name = "valor", nullable = false)
    double valor;

    public Produto(){
    }

    public Produto(double preco){
        this.valor = preco;
    }

    public long getId(){
        return id;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
