package com.pom.spring_ecommerce_livros.repository;

import com.pom.spring_ecommerce_livros.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
