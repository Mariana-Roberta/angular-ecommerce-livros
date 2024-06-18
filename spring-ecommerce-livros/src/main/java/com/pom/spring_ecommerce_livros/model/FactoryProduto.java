package com.pom.spring_ecommerce_livros.model;

public class FactoryProduto {
    String tipo;
    public static Produto getProduto(String tipo, String titulo, String autor, String sinopse, double valor, Integer quantidade){
        if(tipo.equals("LIVRO")){
            return new Livro(titulo, autor, sinopse, valor, quantidade);
        }
        return null;
    }
    public static Produto getProduto(String tipo){
        if(tipo.equals("LIVRO")){
            return new Livro();
        }
        return null;
    }

}
