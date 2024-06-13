package com.pom.spring_ecommerce_livros.repository;

import com.pom.spring_ecommerce_livros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
  @Query("SELECT l FROM Livro l WHERE lower(l.titulo) LIKE lower(concat('%', :term, '%')) OR lower(l.autor) LIKE lower(concat('%', :term, '%')) OR lower(l.sinopse) LIKE lower(concat('%', :term, '%'))")
  List<Livro> searchBooks(@Param("term") String term);
}
