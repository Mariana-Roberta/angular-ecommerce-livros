package com.pom.spring_ecommerce_livros.repository;

import com.pom.spring_ecommerce_livros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
