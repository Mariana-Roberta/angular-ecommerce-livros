import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {CardModule} from "primeng/card";
import {Button, ButtonDirective} from "primeng/button";
import {Router} from "@angular/router";
import {BookService} from "../../services/book.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {InputNumberModule} from "primeng/inputnumber";
import {CartService} from "../../services/cart.service";

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
  @Input() books: any[] = [];
  @Input() quantity: number = 1;
  @Output() quantityChange: EventEmitter<number> = new EventEmitter<number>();
  carrinhoId: number | null = null;
  searchTerm: string = '';

  constructor(private router: Router, private bookService: BookService, private cartService: CartService) {
    this.loadBooks();
  }

  ngOnInit(): void {
    // Inicialize a quantidade para cada livro quando o componente for inicializado
    this.books = this.books.map(book => ({...book, quantity: 1}));
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

  addToCart(livroNome: string, quantidade: number) {
    this.bookService.findBookByName(livroNome).subscribe(book => {
      if (book) {
        const livroId = book.id;
        console.log("Pegou info do livro: " + book.id);

        // Verifica se o carrinho já existe
        if (this.carrinhoId === null) {
          console.log("Carrinho ainda não criado, criando agora...");
          this.cartService.createCart(1).subscribe(carrinho => {
            console.log("carrinho & carrinhoID  " + carrinho + "    |"+ carrinho.id);
            if (carrinho && carrinho.id) {
              console.log("Carrinho criado com sucesso. ID:", carrinho.id);
              this.carrinhoId = carrinho.id;
              this.addOrUpdateItemInCart(livroId, quantidade);
            } else {
              console.error('Erro ao criar o carrinho.');
            }
          });
        } else {
          console.log("Carrinho já existe. ID:", this.carrinhoId);
          this.addOrUpdateItemInCart(livroId, quantidade);
        }
      } else {
        console.error('Livro não encontrado');
      }
    });
  }


  private addOrUpdateItemInCart(livroId: number, quantidade: number) {
    console.log("chegou no addOrUpdate");
    console.log(this.carrinhoId);
    if (this.carrinhoId !== null) {
      this.cartService.getItemsByLivroId(this.carrinhoId, livroId).subscribe(items => {
        console.log("getitemsbylivroid");
        if (items && items.length > 0) {
          // Se o livro já estiver no carrinho, atualize a quantidade
          const itemId = items[0].id; // Supondo que o serviço retorne o ID do item no carrinho
          this.cartService.updateItemQuantity(itemId, quantidade).subscribe(() => {
            console.log('Quantidade do livro atualizada no carrinho.');
          });
        } else {
      console.log("teste para addOrUpdate");
          if (this.carrinhoId !== null) {
            // Se o livro não estiver no carrinho, adicione um novo item
            this.cartService.addItemToCart(this.carrinhoId, livroId, quantidade).subscribe(() => {
              console.log('Livro adicionado ao carrinho.');
            });
          }
        }
      });
    }
  }


  searchBooks(): void {
    this.bookService.searchBooks(this.searchTerm).subscribe(data => {
      this.books = data;
    });
  }

  onQuantityChange(book: any, event: any) {
    book.quantity = event;
    this.quantityChange.emit(book.quantity);
  }

  incrementQuantity(book: any) {
    book.quantity++;
    this.quantityChange.emit(book.quantity);
  }

  decrementQuantity(book: any) {
    if (book.quantity > 1) {
      book.quantity--;
      this.quantityChange.emit(book.quantity);
    }
  }
}
