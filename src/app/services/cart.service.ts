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

  createCart(usuarioId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/criar?usuarioId=${usuarioId}`)
      .pipe(
        map(response => {
          // Certifique-se de que o response é um JSON válido
          return response;
        }),
        catchError(this.handleError)
      );
  }

  addItemToCart(carrinhoId: number, livroId: number, quantidade: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/adicionarItem`, null, {
      params: {
        carrinhoId: carrinhoId.toString(),
        livroId: livroId.toString(),
        quantidade: quantidade.toString()
      }
    });
  }

  getCart() {
    return this.cartSubject.asObservable();
  }

  addToCart(book: any) {
    this.cart.push(book);
    this.cartSubject.next(this.cart);
  }


  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError('Something went wrong; please try again later.');
  }
}
