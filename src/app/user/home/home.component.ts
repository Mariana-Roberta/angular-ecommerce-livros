import {Component, OnInit, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {CardModule} from "primeng/card";
import {Button, ButtonDirective} from "primeng/button";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
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
    ButtonDirective,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  @Input() books: any[] = [];
  quantity: number = 1;
  @Output() quantityChange: EventEmitter<number> = new EventEmitter<number>();
  carrinhoId: number | null = null;
  searchTerm: string = '';
  userAtual: User | null = null;

  constructor(private router: Router, private bookService: BookService, private cartService: CartService, private authService: AuthService) {
    this.userAtual = this.authService.userValue;
    if (this.userAtual !== null) {
      this.authService.setUser(this.userAtual);
      console.log("Recebeu o Usuario", this.userAtual);
    }
  }

  ngOnInit(): void {
    console.log("Realizou ngOnInit()");
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
            this.cartService.setCart(carrinho);
            console.log(this.cartService.cartValue);
          } else {
            console.log("Nenhum carrinho encontrado para o usuário atual. Criando novo carrinho.");
            // ... (Create new cart logic)
          }
        },
        (error) => {
          console.error('Erro ao verificar o carrinho do usuário:', error);
        }
      );
    } else {
      console.error('ID do usuário não encontrado ou inválido.');
    }
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
      if(this.userAtual?.id){
      this.cartService.getCartItemsByUserId(this.userAtual.id).subscribe(items => {
        if (items && items.length > 0) {
          console.log("ITEMS DO CART USUARIO", items);
          const itemId = items[0].id;
          if(this.carrinhoId!==null){ //o item que tiver o idcarrinho e o idlivro vai ser atualizado
          this.cartService.updateItemQuantity(this.carrinhoId, livroId, quantidade).subscribe(() => {
            console.log('Quantidade do livro atualizada no carrinho.');
          });
          } else {
            console.error('Não foi possível atualizar a quantidade do livro no carrinho.');
          }
        } else {
          if (this.carrinhoId !== null) {
            this.cartService.addItemToCart(this.carrinhoId, livroId, quantidade).subscribe(() => {
              console.log('Livro adicionado ao carrinho.');
            });
          }
        }
      });} else {
        console.error('Usuário não encontrado');
      }
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
