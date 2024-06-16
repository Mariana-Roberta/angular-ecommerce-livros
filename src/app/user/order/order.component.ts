import {Component, EventEmitter, Output} from '@angular/core';
import {Button, ButtonDirective} from "primeng/button";
import {InputNumberModule} from "primeng/inputnumber";
import {NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {CartService} from "../../services/cart.service";
import {OrderService} from "../../services/order.service";

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
    FormsModule
  ],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent {
  cartItems: any[] = [];
  pedidoId: number | null = null;

  constructor(private cartService: CartService, private orderService: OrderService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadCartItems();
    this.route.params.subscribe(params => {
      this.pedidoId = +params['id'];
    });
  }

  loadCartItems() {
    this.cartService.getCartItems().subscribe(
      (items: any[]) => {
        console.log(items);
        this.cartItems = items;
      },
      (error) => {
        console.error('Erro ao carregar itens do carrinho:', error);
      }
    );
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
