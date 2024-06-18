package com.pom.spring_ecommerce_livros.repository;

import com.pom.spring_ecommerce_livros.model.ItemCarrinho;
import com.pom.spring_ecommerce_livros.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
