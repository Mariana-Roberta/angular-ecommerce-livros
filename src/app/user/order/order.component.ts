import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Button, ButtonDirective} from "primeng/button";
import {InputNumberModule} from "primeng/inputnumber";
import {NgForOf, NgIf, SlicePipe} from "@angular/common";
import {ActivatedRoute, RouterLink, RouterLinkActive} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {CartService} from "../../services/cart.service";
import {OrderService} from "../../services/order.service";
import {User} from "../../models/user.model";
import {Cart} from "../../models/cart.model";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [
    Button,
    ButtonDirective,
    InputNumberModule,
    NgForOf,
    NgIf,
    RouterLink,
    FormsModule,
    RouterLinkActive,
    SlicePipe
  ],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent implements  OnInit{
  cartItems: any[] = [];
  pedidoId: number | null = null;
  orderTotal: number = 0;
  userAtual: User | null = null;
  cartAtual: Cart | null = null;

  constructor(private cartService: CartService, private orderService: OrderService, private route: ActivatedRoute, private authService: AuthService) {
    this.userAtual = this.authService.userValue;
    if (this.userAtual !== null) {
      this.authService.setUser(this.userAtual);
      console.log("Recebeu o Usuario em Carrinho", this.userAtual);
    }
    this.cartAtual = this.cartService.cartValue;
    if (this.cartAtual !== null) {
      this.cartService.setCart(this.cartAtual);
      console.log("Recebeu o Carrinho", this.cartAtual);
    }
  }

  ngOnInit(): void {
    this.loadCartItems();
    this.calculateOrderTotal();
    this.route.params.subscribe(params => {
      this.pedidoId = +params['id'];
    });
  }

  loadCartItems() {
    if (this.userAtual?.id) {
      this.cartService.getCartItemsByUserId(this.userAtual.id).subscribe(
        (items: any[]) => {
          console.log(items);
          this.cartItems = items;
        },
        (error) => {
          console.error('Erro ao carregar itens do carrinho:', error);
        }
      );
    } else {
      console.error('ID do usuário não encontrado ou inválido.');
    }
  }
  calculateOrderTotal(): void {
    this.orderTotal = 0; // Reset total before calculating
    for (const item of this.cartItems) {
      console.log("VALOR ITEM", item);
      this.orderTotal += item.livro.valor * item.quantidade;
    }
  }

  trackById(index: number, item: any): number {
    return item.id; // Supondo que cada item do carrinho tenha um campo 'id'
  }

  confirmOrder() {
    console.log(this.pedidoId);
    if (this.pedidoId !== null) {
      this.orderService.confirmOrder(this.pedidoId).subscribe(response => {
        console.log('Pedido confirmado com sucesso!', response);
        // Redirecionar ou exibir uma mensagem de sucesso
      });
    }
  }
}
