import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {Button, ButtonDirective} from "primeng/button";
import {InputNumberModule} from "primeng/inputnumber";
import {FormsModule} from "@angular/forms";
import {BookService} from "../../services/book.service";
import {CartService} from "../../services/cart.service";
import {OrderService} from "../../services/order.service";

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    Button,
    NgForOf,
    ButtonDirective,
    InputNumberModule,
    FormsModule
  ],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent {
  cartItems: any[] = [];
  quantity: number = 1;
  orderId: number | null = null;

  @Output() quantityChange = new EventEmitter<number>();

  constructor(private cartService: CartService, private orderService: OrderService, private router: Router) { }

  ngOnInit(): void {
    this.loadCartItems(); // Carregar os itens do carrinho ao inicializar o componente
  }

  // Método para carregar os itens do carrinho
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


  incrementQuantity(item: any) {
    item.quantidade++;
    this.updateItemQuantity(item.id, item.quantidade);
  }

  // Método para decrementar a quantidade de um item
  decrementQuantity(item: any) {
    if (item.quantidade > 1) {
      item.quantidade--;
      this.updateItemQuantity(item.id, item.quantidade);
    }
  }

  updateItemQuantity(itemId: number, quantidade: number) {
    this.cartService.updateItemQuantity(itemId, quantidade).subscribe(
      (response) => {
        // Encontrar o item modificado na lista
        const index = this.cartItems.findIndex(item => item.id === itemId);
        if (index !== -1) {
          this.cartItems[index] = response; // Substituir pelo item atualizado do servidor
        }
      },
      (error) => {
        console.error('Erro ao atualizar quantidade do item:', error);
      }
    );
  }


  removeItem(item: any) {
    this.cartService.removeItemFromCart(item.id).subscribe(
      () => {
        this.cartItems = this.cartItems.filter(cartItem => cartItem.id !== item.id);
      },
      (error) => {
        console.error('Erro ao remover item do carrinho:', error);
      }
    );
  }

  finalizeOrder(): void {
    // Chamar o serviço para finalizar o pedido
    this.orderService.finalizeOrder(this.cartItems[0].carrinho.id)
      .subscribe(
        (novoPedido: any) => {
          console.log('Pedido finalizado com sucesso!', novoPedido);
          console.log(novoPedido.id);
          this.orderId = novoPedido.id; // Supondo que o ID do pedido seja acessível desta forma
          this.router.navigate(['/usuario/pedidos', this.orderId]);
        },
        error => {
          console.error('Erro ao finalizar pedido:', error);
          // Implemente aqui a lógica para lidar com o erro, como exibir uma mensagem de erro para o usuário
        }
      );
  }
}
