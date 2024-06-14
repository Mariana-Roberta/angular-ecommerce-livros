import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {Button, ButtonDirective} from "primeng/button";
import {InputNumberModule} from "primeng/inputnumber";
import {FormsModule} from "@angular/forms";
import {BookService} from "../../services/book.service";

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
  cartItems:any = [];
  books: any[] = [];

  constructor(private router: Router, private bookService: BookService) {
    this.loadBooks();
  }

  loadBooks() {
    this.bookService.getBooks().subscribe(
      (books) => {
        this.books = books;
      },
      (error) => {
        console.error('Erro ao carregar livros', error);
      }
    );
  }

  removeItem(item: any) {
    // Implementar a lógica para remover item do carrinho
    this.cartItems = this.cartItems.filter((cartItem: any) => cartItem !== item);
  }

  @Input() quantity: number = 1;
  @Output() quantityChange: EventEmitter<number> = new EventEmitter<number>(); // Inicialize corretamente aqui

  onQuantityChange(event: any) {
    this.quantityChange.emit(this.quantity);
  }

  incrementQuantity() {
    this.quantity++;
    this.quantityChange.emit(this.quantity);
  }

  decrementQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
      this.quantityChange.emit(this.quantity);
    }
  }
}
