import {Component, Input, OnInit} from '@angular/core';
import {ProdutoService} from "../services/produto.service";

@Component({
  selector: 'app-produto',
  standalone: true,
  imports: [],
  templateUrl: './produto.component.html',
  styleUrl: './produto.component.scss'
})
export class ProdutoComponent implements OnInit{
  @Input() books: any[] = [];

  constructor(private produtoService: ProdutoService) {
  }
  ngOnInit(): void {
    console.log("Realizou ngOnInit()");
    this.loadBooks();
  }

  loadBooks() {
    this.produtoService.getBooks().subscribe(
      (books) => {
        this.books = books;
        console.log(books);
      },
      (error) => {
        console.error('Erro ao carregar livros', error);
      }
    );
  }
}
