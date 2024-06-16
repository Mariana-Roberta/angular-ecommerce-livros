package com.pom.spring_ecommerce_livros.repository;

import com.pom.spring_ecommerce_livros.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
