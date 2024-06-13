import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {CardModule} from "primeng/card";
import {Button, ButtonDirective} from "primeng/button";
import {Router} from "@angular/router";
import {BookService} from "../../services/book.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {InputNumberModule} from "primeng/inputnumber";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgForOf,
    CardModule,
    Button,
    NgIf,
    FormsModule,
    InputTextModule,
    ReactiveFormsModule,
    InputNumberModule,
    ButtonDirective
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  books: any[] = [];
  searchTerm: string = '';

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

  searchBooks(): void {
    this.bookService.searchBooks(this.searchTerm).subscribe(data => {
      this.books = data;
    });
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
