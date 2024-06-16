package com.pom.spring_ecommerce_livros.repository;

import com.pom.spring_ecommerce_livros.model.ItemCarrinho;
import com.pom.spring_ecommerce_livros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
  @Query("DELETE FROM ItemCarrinho ic WHERE ic.livro.id = :livroId")
  void deleteByLivroId(@Param("livroId") Long livroId);

  @Query("SELECT ic FROM ItemCarrinho ic WHERE ic.livro.id = :livroId")
  List<ItemCarrinho> findAllByLivroId(@Param("livroId") Long livroId);

  @Query("SELECT ic FROM ItemCarrinho ic WHERE ic.carrinho.id = :carrinhoId")
  List<ItemCarrinho> findAllByCarrinhoId(@Param("carrinhoId") Long carrinhoId);

  @Query("SELECT ic FROM ItemCarrinho ic WHERE ic.livro.id = :livroId AND ic.carrinho.id = :carrinhoId")
  ItemCarrinho findByCarrinhoIdAndLivroId(@Param("carrinhoId") Long carrinhoId, @Param("livroId") Long livroId);

  @Query("DELETE FROM ItemCarrinho ic WHERE ic.carrinho.id = :carrinhoId")
  void deleteByCarrinhoId(Long carrinhoId);
}
