import { Component } from '@angular/core';
import {CardModule} from "primeng/card";
import {Button} from "primeng/button";
import {NgForOf, NgIf} from "@angular/common";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BookService} from "../../services/book.service";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [
    CardModule,
    Button,
    NgForOf,
    NgIf,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent {
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

  editBook(book: any) {
    this.router.navigate(['/admin/livro/editar'], { state: { book } });
  }

  deleteBook(bookId: number): void {
    this.bookService.deleteBook(bookId).subscribe(() => {
      this.books = this.books.filter(book => book.id !== bookId);
      console.log('Book deleted successfully');
    }, error => {
      console.error('Error deleting book:', error);
    });
  }

}
