import {Component, OnInit, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {CardModule} from "primeng/card";
import {Button, ButtonDirective} from "primeng/button";
import {Router} from "@angular/router";
import {BookService} from "../../services/book.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {InputNumberModule} from "primeng/inputnumber";
import {CartService} from "../../services/cart.service";
import {User} from "../../models/user.model";
import {AuthService} from "../../services/auth.service";

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
  quantity: number = 1;
  @Output() quantityChange: EventEmitter<number> = new EventEmitter<number>();
  carrinhoId: number | null = null;
  searchTerm: string = '';
  userAtual: User | null = null;

  constructor(private router: Router, private bookService: BookService, private cartService: CartService, private authService: AuthService) {
    this.userAtual = this.authService.userValue;
    if(this.userAtual!==null) {
      this.authService.setUser(this.userAtual);
    }
  }

  ngOnInit(): void {
    this.loadBooks();
    this.checkAndLoadCart();
  }

  loadBooks() {
    this.bookService.getBooks().subscribe(
      (books) => {
        this.books = books;
        this.books = this.books.map(book => ({...book, quantity: 1}));
      },
      (error) => {
        console.error('Erro ao carregar livros', error);
      }
    );
  }

  checkAndLoadCart() {
    if (this.userAtual?.id) {
      this.cartService.getCartByUserId(this.userAtual.id).subscribe(
        (carrinho) => {
          if (carrinho && carrinho.id) {
            console.log("Carrinho encontrado. ID:", carrinho.id);
            this.carrinhoId = carrinho.id;
            // Carrega os itens do carrinho, se necessário
            this.cartService.getCartItems();
          } else {
            console.log("Nenhum carrinho encontrado para o usuário atual.");
            if (this.userAtual?.id) {
              if (this.carrinhoId === null) {
                this.cartService.createCart(this.userAtual.id).subscribe(carrinho => {
                  if (carrinho && carrinho.id) {
                    console.log("Carrinho criado com sucesso. ID:", carrinho.id);
                    this.carrinhoId = carrinho.id;
                  } else {
                    console.error('Erro ao criar o carrinho.');
                  }
                });
              } else {
                console.log("Carrinho já existe. ID:", this.carrinhoId);
              }
            } else {
              console.error('ID do usuário não encontrado ou inválido.');
            }
          }
        },
        (error) => {
          console.error('Erro ao verificar o carrinho do usuário', error);
        }
      );
    } else {
      console.error('ID do usuário não encontrado ou inválido.');
    }
  }

  navigateAndReload() {
    this.router.navigate(['/usuario/home']).then(() => {
      this.loadBooks(); // Carrega os livros novamente após a navegação
    });
  }

  addToCart(livroNome: string, quantidade: number) {
    this.bookService.findBookByName(livroNome).subscribe(book => {
      if (book) {
        const livroId = book.id;

        // Verifica se this.authService.userValue?.id não é nulo ou indefinido
        if (this.userAtual?.id) {
          if (this.carrinhoId === null) {
            this.cartService.createCart(this.userAtual.id).subscribe(carrinho => {
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
          console.error('ID do usuário não encontrado ou inválido.');
        }
      } else {
        console.error('Livro não encontrado');
      }
    });
  }



  private addOrUpdateItemInCart(livroId: number, quantidade: number) {
    if (this.carrinhoId !== null) {
      this.cartService.getItemsByLivroId(this.carrinhoId, livroId).subscribe(items => {
        if (items && items.length > 0) {
          const itemId = items[0].id;
          this.cartService.updateItemQuantity(itemId, quantidade).subscribe(() => {
            console.log('Quantidade do livro atualizada no carrinho.');
          });
        } else {
          if (this.carrinhoId !== null) {
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
