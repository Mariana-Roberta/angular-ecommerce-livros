import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: any[] = [];
  private cartSubject = new BehaviorSubject<any[]>(this.cart);
  private apiUrl = 'http://localhost:8080/usuario/carrinho';

  constructor(private http: HttpClient) {}

  // Verifica se um livro está no carrinho
  getItemsByLivroId(carrinhoId: number, livroId: number): Observable<any[]> {
    const url = `${this.apiUrl}/itens?livroId=${livroId}`;
    return this.http.get<any[]>(url);
  }

  getItemsByCarrinhoId(carrinhoId: number): Observable<any[]> {
    const url = `${this.apiUrl}/itens?carrinhoId=${carrinhoId}`;
    return this.http.get<any[]>(url);
  }

  getItemsByCarrinhoIdAndLivroId(carrinhoId: number, livroId: number): Observable<any[]> {
    const url = `${this.apiUrl}/itens?carrinhoId=${carrinhoId}+livroId=${livroId}`;
    return this.http.get<any[]>(url);
  }

  // Atualiza a quantidade de um item no carrinho
  updateItemQuantity(itemId: number, quantidade: number): Observable<any> {
    const url = `${this.apiUrl}/item/${itemId}`;
    const request = { quantidade };

    return this.http.put<any>(url, request)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  createCart(usuarioId: number): Observable<any> {
    const requestBody = { usuarioId: usuarioId };
    return this.http.post<any>(`${this.apiUrl}/criar`, requestBody)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  addItemToCart(carrinhoId: number, livroId: number, quantidade: number): Observable<any> {
    console.log(carrinhoId);
    console.log(livroId);
    console.log(quantidade);
    const itemCarrinho = { carrinhoId, livroId, quantidade };
    return this.http.post<any>(`${this.apiUrl}/addItem`, itemCarrinho)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  getCartByUserId(usuarioId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/usuario/${usuarioId}`)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    return throwError('Something went wrong; please try again later.');
  }
}
