package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.dto.ConfirmarPedidoRequest;
import com.pom.spring_ecommerce_livros.dto.FinalizarPedidoRequest;
import com.pom.spring_ecommerce_livros.model.Pedido;
import com.pom.spring_ecommerce_livros.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario/pedidos")
@CrossOrigin("*")
public class PedidoController {

  @Autowired
  private PedidoService pedidoService;

  @PostMapping("/finalizar")
  public ResponseEntity<Pedido> finalizarPedido(@RequestBody FinalizarPedidoRequest request) {
    Long carrinhoId = request.getCarrinhoId();
    Pedido novoPedido = pedidoService.createPedidoFromCarrinho(carrinhoId);
    return ResponseEntity.ok(novoPedido);
  }

  @PutMapping("/confirmar/{pedidoId}")
  public ResponseEntity<Pedido> confirmarPedido(@PathVariable Long pedidoId, @RequestBody ConfirmarPedidoRequest request) {
    Long carrinhoId = request.getCarrinhoId();
    Pedido pedidoConfirmado = pedidoService.confirmarPedido(pedidoId);
    return ResponseEntity.ok(pedidoConfirmado);
  }
}
